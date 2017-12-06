package com.api.scrubber.launcher;

import static com.api.scrubber.constant.Constants.ENVIRONMENT;

import com.api.scrubber.config.ScrubberConfig;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.launcher.VertxCommandLauncher;
import io.vertx.core.impl.launcher.VertxLifecycleHooks;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

public class ScrubberLauncher extends VertxCommandLauncher implements VertxLifecycleHooks {

	// make sure you have the logger
	static {
		if (System.getProperty("vertx.logger-delegate-factory-class-name") == null) {
			System.setProperty("vertx.logger-delegate-factory-class-name",
					SLF4JLogDelegateFactory.class.getCanonicalName());
		}
		System.out.println(System.getenv(ENVIRONMENT));
	}

	@Override
	public void afterConfigParsed(JsonObject config) {
		ScrubberConfig.INSTANCE.setConfig(config);
	}

	@Override
	public void beforeStartingVertx(VertxOptions options) {

	}

	@Override
	public void afterStartingVertx(Vertx vertx) {

	}

	@Override
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {

	}

	@Override
	public void beforeStoppingVertx(Vertx vertx) {

	}

	@Override
	public void afterStoppingVertx() {

	}

	@Override
	public void handleDeployFailed(Vertx vertx, String mainVerticle, DeploymentOptions deploymentOptions,
			Throwable cause) {
		vertx.close();
	}

	public static void main(String[] args) {
		new ScrubberLauncher().dispatch(args);
	}

	public static void executeCommand(String cmd, String... args) {
		new ScrubberLauncher().execute(cmd, args);
	}
}