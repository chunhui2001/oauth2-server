package com.strategicgains.oauth.token;

import io.netty.handler.codec.http.HttpResponseStatus;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.ServiceException;

import com.strategicgains.oauth.domain.RequestWrapper;

public class TokenController
{
	private OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(
	    new MD5Generator());

	public TokenController()
	{
		super();
	}

	public String token(Request request, Response response)
	{
		try
		{
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(new RequestWrapper(request));
			validateClient(oauthRequest);
			String accessToken = oauthIssuerImpl.accessToken();
			String refreshToken = oauthIssuerImpl.refreshToken();

			//TODO: cache the tokens for verification / refresh.

			OAuthResponse r = OAuthASResponse
			    .tokenResponse(HttpResponseStatus.OK.code())
			    .setTokenType("Bearer")
			    // .setParam("identity", tenant)
			    .setAccessToken(accessToken)
			    .setRefreshToken(refreshToken)
			    .setExpiresIn("3600")
			    .setScope(OAuthUtils.encodeScopes(oauthRequest.getScopes()))
			    .buildJSONMessage();
			return r.getBody();
		}
		catch (OAuthProblemException e)
		{
			response.setResponseStatus(HttpResponseStatus.BAD_REQUEST);
			try
			{
				OAuthResponse r = OAuthASResponse
				    .errorResponse(HttpResponseStatus.BAD_REQUEST.code())
				    .error(e)
				    .buildJSONMessage();
				return r.getBody();
			}
			catch (OAuthSystemException e1)
			{
				throw new ServiceException(e);
			}
		}
		catch (OAuthSystemException e)
		{
			throw new ServiceException(e);
		}
	}

	public Object authorization(Request request, Response response)
	{
		// try
		// {
		// // dynamically recognize an OAuth profile based on request
		// // characteristic (params,
		// // method, content type etc.), perform validation
		// OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
		//
		// // validateRedirectionURI(oauthRequest)
		//
		// // build OAuth response
		// OAuthResponse resp = OAuthASResponse
		// .authorizationResponse(HttpServletResponse.SC_FOUND)
		// .setCode(oauthIssuerImpl.authorizationCode())
		// .location(ex.getRedirectUri()).buildQueryMessage();
		//
		// response.sendRedirect(resp.getLocationUri());
		//
		// // if something goes wrong
		// }
		// catch (OAuthProblemException ex)
		// {
		// final OAuthResponse resp = OAuthASResponse
		// .errorResponse(HttpServletResponse.SC_FOUND).error(ex)
		// .location(redirectUri).buildQueryMessage();
		//
		// response.sendRedirect(resp.getLocationUri());
		// }
		return null;
	}

	public void redirection(Request request, Response response)
	{
	}

	private void validateClient(OAuthTokenRequest oauthRequest)
	{
		switch(GrantType.valueOf(oauthRequest.getGrantType()))
		{
			case AUTHORIZATION_CODE:
				handleAuthorizationCode(oauthRequest);
				break;
			case CLIENT_CREDENTIALS:
				handleClientCredentials(oauthRequest);
				break;
			case PASSWORD:
				handlePassword(oauthRequest);
				break;
			case REFRESH_TOKEN:
				handleRefresh(oauthRequest);
				break;
		}
	}

	private void handleAuthorizationCode(OAuthTokenRequest oauthRequest)
    {
	    // TODO Auto-generated method stub
	    
    }

	private void handleClientCredentials(OAuthTokenRequest oauthRequest)
    {
	    // TODO Auto-generated method stub
	    
    }

	private void handlePassword(OAuthTokenRequest oauthRequest)
    {
	    // TODO Auto-generated method stub
	    
    }

	private void handleRefresh(OAuthTokenRequest oauthRequest)
    {
	    // TODO Auto-generated method stub
	    
    }
}
