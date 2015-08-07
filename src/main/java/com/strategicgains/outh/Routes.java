package com.strategicgains.outh;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes
{
	public static void define(Configuration config, RestExpress server)
    {
		server.uri("/oauth/authorize", config.getSampleController())
			.action("authorize", HttpMethod.GET)
			.name(Constants.Routes.AUTHORIZE);

		server.uri("/oauth/token", config.getSampleController())
			.action("token", HttpMethod.POST)
			.name(Constants.Routes.TOKEN);
    }
}
