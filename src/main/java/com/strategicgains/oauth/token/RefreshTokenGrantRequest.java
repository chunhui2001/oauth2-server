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
public class RefreshTokenGrantRequest
extends AuthorizationGrantRequest
{
	private String refreshToken;

	/**
	 * @param grantType
	 * @param request
	 */
	public RefreshTokenGrantRequest(OauthTokenRequest request)
	{
		super(GrantType.REFRESH_TOKEN, request);
	}

	@Override
	protected void initialize(OauthTokenRequest request)
	{
		this.refreshToken = request.refresh_token;
	}

	public String getRefreshToken()
	{
		return refreshToken;
	}

	@Override
	public void validate()
	{
		if (refreshToken == null)
		{
			throw new Oauth2Exception("Invalid refresh_token");
		}
	}
}
