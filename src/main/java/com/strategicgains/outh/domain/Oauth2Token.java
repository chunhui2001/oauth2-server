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
package com.strategicgains.outh.domain;

/**
 * @author toddf
 * @since Nov 21, 2014
 */
public class Oauth2Token
{
	private int expiresIn;
	private String accessToken;
	private String refreshToken;

	public Oauth2Token expiresIn(int i)
	{
		this.expiresIn = i;
		return this;
	}

	public int expiresIn()
	{
		return expiresIn;
	}

	public String accessToken()
	{
		return accessToken;
	}

	public Oauth2Token accessToken(String accessToken)
	{
		this.accessToken = accessToken;
		return this;
	}

	public String refreshToken()
	{
		return refreshToken;
	}

	public Oauth2Token refreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
		return this;
	}

}
