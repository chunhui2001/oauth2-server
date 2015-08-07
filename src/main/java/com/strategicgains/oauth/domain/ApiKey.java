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
package com.strategicgains.oauth.domain;

import com.strategicgains.syntaxe.annotation.Required;

/**
 * @author toddf
 * @since Mar 30, 2014
 */
public class ApiKey
extends Credentials
{
	@Required("Client ID")
	private String clientId;

	@Required("Client Secret")
	private String clientSecret;

	public ApiKey()
	{
		super();
	}

	public ApiKey(ApiKey that)
    {
		this();

		if (that != null)
		{
			setClientId(that.getClientId());
			setClientSecret(that.getClientSecret());
		}
    }

	/**
	 * @return the clientId
	 */
	public String getClientId()
	{
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	/**
	 * @return the client secret
	 */
	public String getClientSecret()
	{
		return clientSecret;
	}

	/**
	 * Set the secret key value.
	 * 
	 * @param secret the client secret to set
	 */
	public void setClientSecret(String secret)
	{
		this.clientSecret = secret;
	}
}
