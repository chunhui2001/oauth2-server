/*
 * (C)Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import java.util.UUID;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.strategicgains.oauth.Constants;
import com.strategicgains.oauth.domain.AbstractEntity;
import com.strategicgains.oauth.domain.ApiKey;
import com.strategicgains.oauth.domain.Reference;
import com.strategicgains.oauth.tenant.Tenant;
import com.strategicgains.syntaxe.annotation.IntegerValidation;
import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;

/**
 * An Application instance represents a third-party, external application that is registered
 * to use one or more of our APIs.  This registration provides an API key/secret combination
 * for later acquiring an access token.
 *  
 * @author toddf
 * @since Oct 10, 2012
 */
@Entity("applications")
@Indexes(
{
	@Index(value="tenant.id,name",unique=true),
	@Index(name = "application.apiKey", value = "apiKey.clientId", unique = true)
})
public class Application
extends AbstractEntity
{
	// Owning tenant.
	@Required("Tenant")
	private Reference<Tenant> tenant;

	@RegexValidation(name = "Application Name", nullable = false, pattern = Constants.NAME_PATTERN, message = Constants.NAME_MESSAGE)
	private String name;

	private String description;

	private boolean isActive = true;

	// Overrides the (system-wide or tenant-wide) default TTL in seconds for tokens.
	@IntegerValidation(name = "Token TTL (seconds)", min=0)
	private int tokenTTL;

//	private Map<String, Object> extensions;

	@Required("API Key")
	private ApiKey apiKey;

	@Required("Security Salt")
	private String salt;

	public Reference<Tenant> tenant()
	{
		return tenant;
	}

	public boolean hasTenant()
	{
		return (tenant != null);
	}

	public void tenant(Tenant tenant)
	{
		this.tenant = new Reference<Tenant>(tenant);
	}

	public UUID tenantId()
	{
		return (hasTenant() ? tenant.id() : null);
	}

	public void tenantId(UUID uuid)
	{
		this.tenant = new Reference<Tenant>(uuid);
	}

	/**
	 * @return the application name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the application name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void setIsActive(boolean value)
	{
		this.isActive = value;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getTokenTTL()
	{
		return tokenTTL;
	}

	public boolean hasTokenTTL()
	{
		return tokenTTL != 0;
	}

	public void setTokenTTL(int tokenTTL)
	{
		this.tokenTTL = tokenTTL;
	}

	public void setApiKey(ApiKey apiKey)
	{
		this.apiKey = (apiKey == null ? null : new ApiKey(apiKey));
	}

	public ApiKey getApiKey()
	{
		return (apiKey == null ? null : new ApiKey(apiKey));
	}

	/**
	 * @return the apiKey clientId
	 */
	public String getClientId()
	{
		return (apiKey == null ? null : apiKey.getClientId());
	}

	/**
	 * @return the apiKey secret
	 */
	public String getSecret()
	{
		return (apiKey == null ? null : apiKey.getClientSecret());
	}

	/**
	 * Get the Tenant's security salt.
	 * 
	 * @return the security salt.
	 */
	public String getSalt()
	{
		return salt;
	}

	/**
	 * Note that if the salt gets changed, none of the previously-stored
	 * security values (e.g. API key) will be able to be verified.
	 * 
	 * @param securitySalt the new security salt.
	 */
	public void setSalt(String securitySalt)
	{
		this.salt = securitySalt;
	}
}
