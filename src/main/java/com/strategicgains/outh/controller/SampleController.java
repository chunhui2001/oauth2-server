package com.strategicgains.outh.controller;

import java.util.Collections;
import java.util.List;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.UnauthorizedException;

import com.strategicgains.outh.domain.Oauth2Token;
import com.strategicgains.outh.domain.RequestWrapper;

public class SampleController
{
	public SampleController()
	{
		super();
	}

	public Oauth2Token create(Request request, Response response)
	{
		OAuthTokenRequest oauthRequest = null;
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try
		{
			oauthRequest = new OAuthTokenRequest(new RequestWrapper(request));

//			validateClient(oauthRequest);

			String authzCode = oauthRequest.getCode();

			// some code

			String accessToken = oauthIssuerImpl.accessToken();
			String refreshToken = oauthIssuerImpl.refreshToken();

			return new Oauth2Token()
				.expiresIn(3600)
				.accessToken(accessToken)
				.refreshToken(refreshToken);
		}
		catch (OAuthProblemException | OAuthSystemException ex)
		{
			throw new UnauthorizedException(ex);
		}
	}

	public Object read(Request request, Response response)
	{
		// TODO: Your 'GET' logic here...
		return null;
	}

	public List<Object> readAll(Request request, Response response)
	{
		// TODO: Your 'GET collection' logic here...
		return Collections.emptyList();
	}

	public void update(Request request, Response response)
	{
		// TODO: Your 'PUT' logic here...
		response.setResponseNoContent();
	}

	public void delete(Request request, Response response)
	{
		// TODO: Your 'DELETE' logic here...
		response.setResponseNoContent();
	}
}
