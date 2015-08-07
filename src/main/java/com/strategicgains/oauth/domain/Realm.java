package com.strategicgains.oauth.domain;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;

import com.strategicgains.oauth.token.Oauth2Token;

public interface Realm
{
	void validate(OAuthTokenRequest oauthRequest);
	Oauth2Token newToken(String accessToken, String refreshToken);
}
