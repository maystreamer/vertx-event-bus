package com.api.scrubber.util;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.reactivex.core.Future;
import io.vertx.reactivex.core.Vertx;

public class VertxUtil {

	public static <T> void executeBlocking(Vertx vertx, Handler<Future<T>> future, boolean ordered, Handler<AsyncResult<T>> handler) {
		vertx.executeBlocking(future, ordered, handler);
	}

	public static String getIpAddress() {
		try {
			Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
			while (networkInterface.hasMoreElements()) {
				for (InterfaceAddress ifa : networkInterface.nextElement().getInterfaceAddresses())
					if (ifa.getAddress().isSiteLocalAddress())
						return ifa.getAddress().getHostAddress();
			}
		} catch (SocketException e) {

		}
		return "127.0.0.1";
	}
}