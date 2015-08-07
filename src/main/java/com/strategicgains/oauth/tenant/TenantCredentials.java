/*
 * Copyright 2014, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import java.util.UUID;

import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * @author toddf
 * @since Mar 31, 2014
 */
public class TenantCredentials
implements Linkable
{
	private UUID tenantId;
	private String tenantName;
	private String clientId;
	private String clientSecret;

    public TenantCredentials(UUID id, String name, String clientId, String secret)
    {
	    super();
	    this.tenantId = id;
	    this.tenantName = name;
	    this.clientId = clientId;
	    this.clientSecret = secret;
    }

    public TenantCredentials(Tenant organization)
    {
    	this(organization.getUuid(), organization.getName(), organization.getClientId(), organization.getSecret());
    }

    public UUID getTenantId()
    {
    	return tenantId;
    }

	public String getTenantName()
	{
		return tenantName;
	}

	public String getClientId()
	{
		return clientId;
	}

	public String getClientSecret()
	{
		return clientSecret;
	}
}
