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
 * @since Mar 30, 2014
 */
public class ApiKeyGenerator
{
	// CONSTANTS

	private static final int CLIENT_ID_BYTES = 16;
	private static final int CLIENT_SECRET_BYTES = 32;
	private static final int SALT_BYTES = 32;


	// INSTANCE VARABLES

	private SecureRandomNumberGenerator clientIdGenerator;
	private SecureRandomNumberGenerator clientSecretGenerator;
	private SecureRandomNumberGenerator saltGenerator;


	// CONSTRUCTOR

	public ApiKeyGenerator()
	{
		this(CLIENT_ID_BYTES, CLIENT_SECRET_BYTES, SALT_BYTES);
	}

	public ApiKeyGenerator(int idBytes, int secretBytes, int saltBytes)
	{
		super();
		clientIdGenerator = new SecureRandomNumberGenerator();
		clientIdGenerator.setDefaultNextBytesSize(idBytes);;
		clientSecretGenerator = new SecureRandomNumberGenerator();
		clientSecretGenerator.setDefaultNextBytesSize(secretBytes);
		saltGenerator = new SecureRandomNumberGenerator();
		saltGenerator.setDefaultNextBytesSize(saltBytes);
	}

	public ApiKey generateApiKey()
    {
		ApiKey apiKey = new ApiKey();
		apiKey.setClientId(clientIdGenerator.nextBytes().toBase64());
		apiKey.setClientSecret(clientSecretGenerator.nextBytes().toBase64());
		return apiKey;
    }

	public String generateSalt()
	{
		return saltGenerator.nextBytes().toBase64();
	}
}
