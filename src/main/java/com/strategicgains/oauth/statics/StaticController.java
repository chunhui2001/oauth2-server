package com.strategicgains.oauth.statics;

import java.util.List;
import java.util.Map;

import com.strategicgains.oauth.domain.StaticResource;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.restexpress.Request;
import org.restexpress.Response;

import com.strategicgains.oauth.realm.Realm;

import io.netty.handler.codec.http.HttpHeaders;

import org.restexpress.RestExpress;

public class StaticController
{

	public StaticController()
	{
		super();
	}

	public StaticResource read(Request request, Response response)
	{

		return new StaticResource(request.getPath());
	}

}
