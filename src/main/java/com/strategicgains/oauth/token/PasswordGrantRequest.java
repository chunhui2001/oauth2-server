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
public class PasswordGrantRequest
extends AuthorizationGrantRequest
{
	private String username;
	private String password;

	/**
	 * @param grantType
	 */
	public PasswordGrantRequest(OauthTokenRequest request)
	{
		super(GrantType.PASSWORD, request);
	}

	@Override
	protected void initialize(OauthTokenRequest request)
	{
		this.username = request.username;
		this.password = request.password;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	@Override
	public void validate()
	{
		if (username == null || password == null)
		    throw new Oauth2Exception("Invalid username or password");
	}
}
