package com.strategicgains.oauth;

import io.netty.handler.codec.http.HttpMethod;

import static com.strategicgains.oauth.Constants.Routes.APPLICATION;
import static com.strategicgains.oauth.Constants.Routes.APPLICATION_COLLECTION;
import static com.strategicgains.oauth.Constants.Routes.APPLICATION_CREDENTIALS;
import static com.strategicgains.oauth.Constants.Routes.TENANT;
import static com.strategicgains.oauth.Constants.Routes.TENANT_CREDENTIALS;
import static com.strategicgains.oauth.Constants.Routes.TENANT_COLLECTION;
import static com.strategicgains.oauth.Constants.Routes.TENANT_PARENT;
import static io.netty.handler.codec.http.HttpMethod.DELETE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpMethod.PUT;



import static com.strategicgains.oauth.Constants.Routes.INDEX_ROUTE;

import org.restexpress.Flags;
import org.restexpress.RestExpress;
import com.strategicgains.oauth.statics.StaticController;

public abstract class Routes
{
	public static void define(Configuration config, RestExpress server)
    {

		server.uri("/oauth/token", config.getTokenController())
			.action("token", HttpMethod.POST)
			.name(Constants.Routes.TOKEN)
			.flag(Flags.Auth.PUBLIC_ROUTE);

		server.uri("/tenants", config.getTenantController())
			.method(POST)
			.action("readAll", GET)
			.name(TENANT_COLLECTION);
	
		server.uri("/tenants/{tenantId}", config.getTenantController())
			.method(GET, PUT, DELETE)
			.name(TENANT);

		server.uri("/tenants/{tenantId}/parent", config.getTenantController())
			.action("readParent", GET)
			.name(TENANT_PARENT);
	
		server.uri("/tenants/{tenantId}/credentials", config.getTenantController())
			.action("readCredentials", GET)
			.action("resetCredentials", PUT)
			.name(TENANT_CREDENTIALS);

	    server.uri("/applications", config.getApplicationController())
	    	.alias("/apps")
			.method(POST)
			.action("readAll", GET)
			.name(APPLICATION_COLLECTION);
	
		server.uri("/applications/{applicationId}", config.getApplicationController())
			.alias("/apps/{applicationId}")
			.method(GET, PUT, DELETE)
			.name(APPLICATION);
	
		server.uri("/applications/{applicationId}/credentials", config.getApplicationController())
			.alias("/apps/{applicationId}/credentials")
			.action("readCredentials", GET)
			.action("resetCredentials", PUT)
			.name(APPLICATION_CREDENTIALS);



		server.uri("/index|/index.{format}", config.getHomeController())
				.method(HttpMethod.GET)
				.name(INDEX_ROUTE);
    }
}
