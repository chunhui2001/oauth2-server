package com.strategicgains.outh.token;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.UnauthorizedException;

import com.strategicgains.outh.domain.RequestWrapper;

public class TokenController
{
	public TokenController()
	{
		super();
	}

	public Oauth2Token create(Request request, Response response)
	{
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try
		{
			OAuthTokenRequest oauthRequest = new OAuthTokenRequest(new RequestWrapper(request));
			String authzCode = oauthRequest.getCode();
//			validateClient(oauthRequest);

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

	public Object authorize(Request request, Response response)
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
}
