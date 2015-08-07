/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import java.util.UUID;

import org.restexpress.plugin.hyperexpress.Linkable;

/**
 * @author toddf
 * @since Nov 12, 2012
 */
public class ApplicationCredentials
implements Linkable// , CredentialsResponse
{
	private UUID applicationId;
	private String applicationName;
	private String clientId;
	private String clientSecret;

    public ApplicationCredentials(UUID id, String name, String clientId, String secret)
    {
	    super();
	    this.applicationId = id;
	    this.applicationName = name;
	    this.clientId = clientId;
	    this.clientSecret = secret;
    }

    public ApplicationCredentials(Application application)
    {
    	this(application.getUuid(), application.getName(), application.getClientId(), application.getSecret());
    }

    public UUID getApplicationId()
    {
    	return applicationId;
    }

	public String getApplicationName()
	{
		return applicationName;
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
