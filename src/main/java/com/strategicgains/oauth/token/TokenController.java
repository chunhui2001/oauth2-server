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
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.ServiceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.strategicgains.oauth.domain.RequestWrapper;

public class TokenController
{
	private OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
	
	public TokenController()
	{
		super();
	}

	public String token(Request request, Response response)
	{
		try
		{
			HttpServletRequest servletRequest = null;
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(new RequestWrapper(request));
			validateClientId(oauthRequest);
			String accessToken = oauthIssuerImpl.accessToken();
			String refreshToken = oauthIssuerImpl.refreshToken();

			OAuthResponse r = OAuthASResponse
				.tokenResponse(HttpResponseStatus.OK.code())
				.setAccessToken(accessToken)
				.setExpiresIn("3600")
				.setRefreshToken(refreshToken)
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
		catch(OAuthSystemException e)
		{
			throw new ServiceException(e);
		}
	}

	public Object authorization(Request request, Response response)
	{
//		try
//		{
//			// dynamically recognize an OAuth profile based on request
//			// characteristic (params,
//			// method, content type etc.), perform validation
//			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
//
//			// validateRedirectionURI(oauthRequest)
//
//			// build OAuth response
//			OAuthResponse resp = OAuthASResponse
//			    .authorizationResponse(HttpServletResponse.SC_FOUND)
//			    .setCode(oauthIssuerImpl.authorizationCode())
//			    .location(ex.getRedirectUri()).buildQueryMessage();
//
//			response.sendRedirect(resp.getLocationUri());
//
//			// if something goes wrong
//		}
//		catch (OAuthProblemException ex)
//		{
//			final OAuthResponse resp = OAuthASResponse
//			    .errorResponse(HttpServletResponse.SC_FOUND).error(ex)
//			    .location(redirectUri).buildQueryMessage();
//
//			response.sendRedirect(resp.getLocationUri());
//		}
		return null;
	}

	public void redirection(Request request, Response response)
	{
	}

    private void validateClientId(OAuthTokenRequest oauthRequest)
    {
	    // TODO Auto-generated method stub
    }
}
