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

import com.strategicgains.syntaxe.Validatable;

/**
 * An abstract class representing the root of a little inheritance hierarchy
 * reflecting the types of OAuth2 grant-type requests possible.
 * <p/>
 * The initialize() method parses appropriate values from an AccessTokenRequest,
 * which simply contains the raw data.
 * 
 * @author toddf
 * @since Jun 1, 2014
 */
public abstract class AuthorizationGrantRequest
implements Validatable
{
	private GrantType grantType;

	public AuthorizationGrantRequest(GrantType grantType, OauthTokenRequest request)
	{
		super();
		this.grantType = grantType;
		initialize(request);
	}

	public GrantType getGrantType()
	{
		return grantType;
	}

	protected abstract void initialize(OauthTokenRequest request);
}
