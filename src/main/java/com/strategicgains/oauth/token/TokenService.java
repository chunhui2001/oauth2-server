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

import org.restexpress.exception.NotFoundException;

import com.strategicgains.oauth.Oauth2Exception;
import com.strategicgains.oauth.application.Application;
import com.strategicgains.oauth.application.ApplicationRepository;
import com.strategicgains.oauth.domain.TokenGenerator;
import com.strategicgains.oauth.tenant.Tenant;
import com.strategicgains.oauth.tenant.TenantRepository;

/**
 * @author toddf
 * @since May 4, 2014
 */
public class TokenService
{
	private TenantRepository tenants;
	private ApplicationRepository apps;
	private TokenGenerator tokens;
	private int tokenTTL;

	public TokenService(TenantRepository tenantRepo, ApplicationRepository appRepo, TokenGenerator tokenGenerator, int tokenTTL)
	{
		super();
		this.tenants = tenantRepo;
		this.apps = appRepo;
		this.tokens = tokenGenerator;
		this.tokenTTL = tokenTTL;
	}

	public OauthTokenResponse verifyToken(String token)
    {
		throw new Oauth2Exception("Invalid token");
    }

	public OauthTokenResponse createAccessToken(OauthTokenRequest tr)
    {
		AuthorizationGrantRequest gr = tr.createAuthorizationGrantRequest();
		gr.validate();

	    return (tr.isPasswordGrant() ? generatePasswordGrant((PasswordGrantRequest) gr) : generateClientCredentialsGrant((ClientCredentialsGrantRequest) gr));
    }

	private OauthTokenResponse generatePasswordGrant(PasswordGrantRequest tr)
	{
		throw new Oauth2Exception("Password grant not currently supported");
	}

	private OauthTokenResponse generateClientCredentialsGrant(ClientCredentialsGrantRequest tr)
    {
		try
		{
			if (tr.isApplicationScope())
			{
				return generateApplicationToken(tr);
			}
			else if (tr.isOrganizationScope())
			{
				return generateOrganizationToken(tr);
			}
		}
		catch (NotFoundException e)
		{
			throw new Oauth2Exception(e);
		}

		throw new Oauth2Exception("Invalid token request");
    }

	private OauthTokenResponse generateOrganizationToken(ClientCredentialsGrantRequest tr)
	{
		Tenant tenant = tenants.readByClientId(tr.getClientId());

		if (!tr.getClientSecret().equals(tenant.getSecret()))
		{
			throw new Oauth2Exception("Invalid client_secret: " + tr.getClientSecret());
		}

		tenant.setApiKey(null);
		tenant.setSalt(null);

		OauthTokenResponse token = new OauthTokenResponse();
		token.identity = tenant;
		token.access_token = tokens.generateToken();
		token.refresh_token = tokens.generateRefreshToken();
		token.expires_in = (tenant.hasTokenTTL() ? tenant.getTokenTTL() : tokenTTL);
		token.scope = tr.getScope();
		return token;
	}

	private OauthTokenResponse generateApplicationToken(ClientCredentialsGrantRequest tr)
    {
	    Application app = apps.readByClientId(tr.getClientId());

		if (!tr.getClientSecret().equals(app.getSecret()))
		{
			throw new Oauth2Exception("Invalid client_secret: " + tr.getClientSecret());
		}

		app.setApiKey(null);
		app.setSalt(null);

		OauthTokenResponse token = new OauthTokenResponse();
		token.identity = app;
		token.access_token = tokens.generateToken();
		token.refresh_token = tokens.generateRefreshToken();
		token.expires_in = (app.hasTokenTTL() ? app.getTokenTTL() : tokenTTL);
		token.scope = tr.getScope();
		return token;
    }
}
