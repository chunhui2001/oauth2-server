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

import org.apache.shiro.crypto.SecureRandomNumberGenerator;

/**
 * @author toddf
 * @since June 18, 2014
 */
public class TokenGenerator
{
	// CONSTANTS

	private static final int TOKEN_BYTES = 16;
	private static final int REFRESH_TOKEN_BYTES = 32;


	// INSTANCE VARABLES

	private SecureRandomNumberGenerator tokenGenerator;
	private SecureRandomNumberGenerator refreshTokenGenerator;


	// CONSTRUCTOR

	public TokenGenerator()
	{
		this(TOKEN_BYTES, REFRESH_TOKEN_BYTES);
	}

	public TokenGenerator(int tokenBytes, int refreshTokenBytes)
	{
		super();
		tokenGenerator = new SecureRandomNumberGenerator();
		tokenGenerator.setDefaultNextBytesSize(tokenBytes);
		refreshTokenGenerator = new SecureRandomNumberGenerator();
		refreshTokenGenerator.setDefaultNextBytesSize(refreshTokenBytes);
	}

	public String generateToken()
    {
		return tokenGenerator.nextBytes().toBase64();
    }

	public String generateRefreshToken()
    {
		return refreshTokenGenerator.nextBytes().toBase64();
    }
}
