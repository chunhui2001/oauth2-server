/*
    Copyright 2014, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package com.strategicgains.oauth.token;

import java.util.List;
import java.util.Map;

import com.strategicgains.oauth.Oauth2Exception;

/**
 * A simple container object for the parameters a client may submit for an access
 * token request.  Used as raw data to construct a {@link AuthorizationGrantRequest} sub-class.
 * 
 * @author toddf
 * @since May 4, 2014
 */
public class OauthTokenRequest
{
	public String grant_type;
	public String client_id;
	public String client_secret;
	public String username;
	public String password;
	public String refresh_token;
	public String state;
	public String redirection_uri;
	public String response_type;

	/*
	 * The value of the scope parameter is expressed as a list of space-delimited,
	 * case-sensitive strings.  If the value contains multiple space-delimited
	 * strings, their order does not matter, and each string adds an additional
	 * access range to the requested scope.
	 * 
	 * Optional.
	 */
	public String scope;

	public OauthTokenRequest()
	{
		super();
	}

	public OauthTokenRequest(Map<String, List<String>> params)
	{
		List<String> values = params.get("grant_type");
		if (values != null && !values.isEmpty()) grant_type = values.get(0);

		values = params.get("client_id");
		if (values != null && !values.isEmpty()) client_id = values.get(0);

		values = params.get("client_secret");
		if (values != null && !values.isEmpty()) client_secret = values.get(0);

		values = params.get("username");
		if (values != null && !values.isEmpty()) username = values.get(0);

		values = params.get("password");
		if (values != null && !values.isEmpty()) password = values.get(0);

		values = params.get("scope");
		if (values != null && !values.isEmpty()) scope = values.get(0);

		values = params.get("refresh_token");
		if (values != null && !values.isEmpty()) refresh_token = values.get(0);

		values = params.get("redirection_uri");
		if (values != null && !values.isEmpty()) redirection_uri = values.get(0);

		values = params.get("state");
		if (values != null && !values.isEmpty()) state = values.get(0);

		values = params.get("response_type");
		if (values != null && !values.isEmpty()) response_type = values.get(0);
	}

	public boolean isPasswordGrant()
	{
		return (GrantType.PASSWORD.equals(GrantType.from(grant_type)));
	}

	public boolean isClientCredentialsGrant()
	{
		return (GrantType.CLIENT_CREDENTIALS.equals(GrantType.from(grant_type)));
	}

	public boolean isRefreshTokenGrant()
	{
		return (GrantType.REFRESH_TOKEN.equals(GrantType.from(grant_type)));
	}

	public boolean isAuthorizationCodeGrant()
	{
		return (GrantType.AUTHORIZATION_CODE.equals(GrantType.from(grant_type)));
	}
	
	public AuthorizationGrantRequest createAuthorizationGrantRequest()
	{
		GrantType grantType = GrantType.from(grant_type);

		switch (grantType)
		{
			case PASSWORD: 
				return new PasswordGrantRequest(this);
			case CLIENT_CREDENTIALS:
				return new ClientCredentialsGrantRequest(this);
			case REFRESH_TOKEN:
				return new RefreshTokenGrantRequest(this);
			case AUTHORIZATION_CODE:
				return new AuthorizationCodeGrantRequest(this);
			case IMPLICIT:
				return new ImplicitTokenGrantRequest(this);
			default:
				throw new Oauth2Exception("Invalid token request");
		}
	}
}
