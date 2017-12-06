package com.api.scrubber.verticle;

import java.util.HashSet;
import java.util.Set;

import com.api.scrubber.annotation.RouteProcessor;
import com.api.scrubber.config.ScrubberConfig;
import com.api.scrubber.handler.ScrubberErrorHandler;
import com.api.scrubber.helper.MediaType;

import io.reactivex.Single;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import io.vertx.reactivex.ext.web.handler.ResponseContentTypeHandler;

public class HttpServerVerticle extends BaseVerticle {
	protected static Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
	public static final String CONFIG_HTTP_SERVER_PORT = "http_server_port";
	public static final String CONFIG_HTTP_SERVER_HOST = "http_server_host";
	public static final String CONFIG_IS_HTTP2_ENABLED = "http2Enabled";
	public static final String CONFIG_HTTP_SERVER_OPTIONS = "http_server_options";
	private Single<HttpServer> server;
	private Router mainRouter;

	@Override
	public void start(Future<Void> startFuture) {
		try {
			super.start();
			this.server = createHttpServer(createOptions(isHTTP2Enabled()), buildRouter());
			server.subscribe(result -> {
				startFuture.complete();
				logger.info("HTTP server running on port {}", result.actualPort());
			});
		} catch (Exception ex) {
			logger.error("Failed to start HTTP Server", ex);
			startFuture.fail(ex);
		}
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
		mainRouter.getRoutes().forEach(r -> r.remove());
		server.blockingGet().close(result -> {
			if (result.succeeded()) {
				logger.info("HttpServerVerticle is closed successfully");
			} else {
				logger.error("Error while closing HttpServerVerticle ", result.cause());
			}
			server = null;
		});
	}

	private Single<HttpServer> createHttpServer(final HttpServerOptions httpOptions, final Router router) {
		return vertx.createHttpServer(httpOptions).requestHandler(router::accept).rxListen(getPort(), getHost());
	}

	private HttpServerOptions createOptions(boolean http2) {
		HttpServerOptions serverOptions = new HttpServerOptions(getHTTPServerOptions());
		if (http2) {
			serverOptions.setSsl(true)
					.setKeyCertOptions(
							new PemKeyCertOptions().setCertPath("server-cert.pem").setKeyPath("server-key.pem"))
					.setUseAlpn(true);
		}
		return serverOptions;
	}

	private Router buildRouter() {
		this.mainRouter = Router.router(vertx).exceptionHandler((error -> {
			logger.error("Routers not injected", error);
		}));
		mainRouter.route(CONTEXT_PATH + "/*").handler(BodyHandler.create());
		mainRouter.route().handler(ResponseContentTypeHandler.create());
		mainRouter.route(CONTEXT_PATH + "/*").handler(
				CorsHandler.create("*").allowedHeaders(getAllowedHeaders()).exposedHeaders(getAllowedHeaders()));
		mainRouter.route(CONTEXT_PATH + "/*").consumes(MediaType.APPLICATION_JSON_VALUE);
		mainRouter.route(CONTEXT_PATH + "/*").produces(MediaType.APPLICATION_JSON_VALUE);
		RouteProcessor.init(this.mainRouter, vertx);
		mainRouter.route(CONTEXT_PATH + "/*").last().failureHandler(new ScrubberErrorHandler(vertx));
		return this.mainRouter;
	}

	private Set<String> getAllowedHeaders() {
		Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("X-Requested-With");
		allowHeaders.add("Access-Control-Allow-Origin");
		allowHeaders.add("Origin");
		allowHeaders.add("Content-Type");
		allowHeaders.add("Accept");
		return allowHeaders;
	}

	private int getPort() {
		return ScrubberConfig.INSTANCE.getConfig().getInteger(CONFIG_HTTP_SERVER_PORT, 8080);
	}

	private String getHost() {
		return ScrubberConfig.INSTANCE.getConfig().getString(CONFIG_HTTP_SERVER_HOST, "localhost");
	}

	private boolean isHTTP2Enabled() {
		return ScrubberConfig.INSTANCE.getConfig().getBoolean(CONFIG_IS_HTTP2_ENABLED, false);
	}

	private JsonObject getHTTPServerOptions() {
		return ScrubberConfig.INSTANCE.getConfig().getJsonObject(CONFIG_HTTP_SERVER_OPTIONS, new JsonObject());
	}
}