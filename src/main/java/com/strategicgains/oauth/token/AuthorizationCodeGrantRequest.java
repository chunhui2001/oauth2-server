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
public class AuthorizationCodeGrantRequest
extends AuthorizationGrantRequest
{
	private String responseType;
	private String clientId;
	private String redirectionUri;
	private String scope;
	private String state;

	/**
	 * @param grantType
	 * @param request
	 */
	public AuthorizationCodeGrantRequest(OauthTokenRequest request)
	{
		super(GrantType.AUTHORIZATION_CODE, request);
	}

	@Override
	protected void initialize(OauthTokenRequest request)
	{
		this.clientId = request.client_id;
		this.scope = request.scope;
		this.responseType = request.response_type;
		this.redirectionUri = request.redirection_uri;
		this.state = request.state;
	}

	public String getResponseType()
	{
		return responseType;
	}

	public String getClientId()
	{
		return clientId;
	}

	public String getRedirectionUri()
	{
		return redirectionUri;
	}

	public String getScope()
	{
		return scope;
	}

	public String getState()
	{
		return state;
	}

	@Override
	public void validate()
	{
		if (clientId == null || clientId.trim().isEmpty())
		{
			throw new Oauth2Exception("Invalid client_id");
		}

		if (!"code".equals(responseType))
		{
			throw new Oauth2Exception("Invalid response_type: " + responseType);
		}
	}
}
