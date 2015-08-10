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

import com.strategicgains.oauth.Oauth2Exception;

/**
 * OAuth2 Grant Types.
 * 
 * @author toddf
 * @since Jun 1, 2014
 * @see http://tools.ietf.org/html/rfc6749#section-1.3
 */
public enum GrantType
{
	// see: http://tools.ietf.org/html/rfc6749#section-4.1
	AUTHORIZATION_CODE("authorization_code"),

	// see: http://tools.ietf.org/html/rfc6749#section-4.2
	IMPLICIT("implicit"),

	// see: http://tools.ietf.org/html/rfc6749#section-4.3
	PASSWORD("password"),

	// see: http://tools.ietf.org/html/rfc6749#section-4.4
	CLIENT_CREDENTIALS("client_credentials"),

	// see: http://tools.ietf.org/html/rfc6749#section-6
	REFRESH_TOKEN("refresh_token");

	private String value;

	private GrantType(String value)
	{
		this.value = value;
	}

	public String value()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return value();
	}

	public static GrantType from(String grantType)
	{
		if (grantType == null || grantType.trim().isEmpty())
		{
			throw new Oauth2Exception("Invalid grant type: null or empty");
		}

		if (PASSWORD.value().equalsIgnoreCase(grantType))
		{
			return PASSWORD;
		}

		if (CLIENT_CREDENTIALS.value().equalsIgnoreCase(grantType))
		{
			return CLIENT_CREDENTIALS;
		}

		if (IMPLICIT.value().equalsIgnoreCase(grantType))
		{
			return IMPLICIT;
		}

		if (REFRESH_TOKEN.value().equalsIgnoreCase(grantType))
		{
			return REFRESH_TOKEN;
		}

		if (AUTHORIZATION_CODE.value().equalsIgnoreCase(grantType))
		{
			return AUTHORIZATION_CODE;
		}

		throw new IllegalArgumentException("Invalid grant type: " + grantType);
	}
}
