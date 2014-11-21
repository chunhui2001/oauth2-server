package com.strategicgains.outh;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;
import com.strategicgains.outh.config.Configuration;

public abstract class Routes
{
	public static void define(Configuration config, RestExpress server)
    {
		//TODO: Your routes here...
		server.uri("/oauth/token", config.getSampleController())
			.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
			.name(Constants.Routes.SINGLE_SAMPLE);

		server.uri("/oauth/token", config.getSampleController())
			.method(HttpMethod.POST)
			.name(Constants.Routes.SAMPLE_COLLECTION);
// or...
//		server.regex("/some.regex", config.getRouteController());
    }
}
