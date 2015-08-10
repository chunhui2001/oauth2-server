package com.strategicgains.oauth.token;

import java.util.List;
import java.util.Map;

import org.restexpress.Request;
import org.restexpress.Response;

import com.strategicgains.oauth.realm.Realm;

import io.netty.handler.codec.http.HttpHeaders;

public class TokenController
{
	private TokenService tokens;
	private Realm realm;

	public TokenController(TokenService tokenService, Realm realm)
	{
		super();
		this.tokens = tokenService;
		this.realm = realm;
	}

	public OauthTokenResponse token(Request request, Response response)
	{
		OauthTokenRequest tr = null;

		if (isFormEncoded(request))
		{
			tr = new OauthTokenRequest(request.getBodyFromUrlFormEncoded(false));
		}
		else
		{
			tr = request.getBodyAs(OauthTokenRequest.class, "Token request details not provided.");
		}

		// TODO: Perform semantic validation on the incoming OauthTokenRequest (via Realm).
		return tokens.createAccessToken(tr);
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

	private boolean isFormEncoded(Request request)
    {
		String contentType = request.getHeader(HttpHeaders.Names.CONTENT_TYPE);
		return (contentType == null ? false : contentType.startsWith("application/x-www-form-urlencoded"));
    }
}
