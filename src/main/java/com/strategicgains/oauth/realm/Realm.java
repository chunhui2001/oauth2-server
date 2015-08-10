package com.strategicgains.oauth.realm;

import com.strategicgains.oauth.token.OauthTokenRequest;
import com.strategicgains.oauth.token.OauthTokenResponse;

/**
 * A Realm is an adapter between the OAuth2 server and the user/identity-management (IdM) storage system,
 * like LDAP, Active Directory, and JDBC.
 * 
 * @author fredta2
 * @since Aug 7, 2015
 */
public interface Realm
{
	void validate(OauthTokenRequest oauthRequest);
	OauthTokenResponse newToken(String accessToken, String refreshToken);
}
