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
 * @author toddf
 * @since Jun 1, 2014
 */
public class ClientCredentialsGrantRequest
extends AuthorizationGrantRequest
{
	// SECTION: CONSTANTS
	public static final String APPLICATION_SCOPE = "application";
	public static final String ORGANIZATION_SCOPE = "organization";

	private String clientId;
	private String clientSecret;
	private String scope;

	/**
	 * @param grantType
	 */
	public ClientCredentialsGrantRequest(OauthTokenRequest request)
	{
		super(GrantType.CLIENT_CREDENTIALS, request);
	}

	@Override
	protected void initialize(OauthTokenRequest request)
	{
		this.clientId = request.client_id;
		this.clientSecret = request.client_secret;
		this.scope = request.scope;
	}

	public boolean isApplicationScope()
	{
		return (APPLICATION_SCOPE.equals(scope));
	}

	public boolean isOrganizationScope()
	{
		return (ORGANIZATION_SCOPE.equals(scope));
	}

	public String getClientId()
	{
		return clientId;
	}

	public String getClientSecret()
	{
		return clientSecret;
	}

	public String getScope()
	{
		return scope;
	}

	@Override
	public void validate()
	{
		if (clientId == null || clientId.trim().isEmpty())
		{
			throw new Oauth2Exception("Invalid client_id");
		}
		if (clientSecret == null || clientSecret.trim().isEmpty())
		{
			throw new Oauth2Exception("Invalid client_secret");
		}

		if (!isApplicationScope() && !isOrganizationScope())
		{
			throw new Oauth2Exception("Invalid scope: " + scope);
		}
	}
}
