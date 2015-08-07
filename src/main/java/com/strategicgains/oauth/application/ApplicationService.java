/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;

import com.strategicgains.apicore.exception.InvalidKeyException;
import com.strategicgains.apicore.service.ApiKeyGenerator;
import com.strategicgains.repoexpress.adapter.Identifiers;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationService
{
	// INSTANCE VARIABLES

	private ApplicationRepository applications;
	private ApiKeyGenerator apiKeys;

	
	// CONSTRUCTOR

	public ApplicationService(ApplicationRepository appRepo, ApiKeyGenerator keyGenerator)
	{
		super();
		this.applications = appRepo;
		this.apiKeys = keyGenerator;
	}

	public Application create(Application application)
	throws IOException
	{
		// Ensure the application ID is unique.
		if (applications.readByName(application.tenantId(), application.getName()) != null)
		{
			throw new ConflictException("Application name already taken: " + application.getName());
		}

		application.setApiKey(apiKeys.generateApiKey());
		application.setSalt(apiKeys.generateSalt());
		ValidationEngine.validateAndThrow(application);
		return applications.create(application);
	}

	/**
	 * Read an Application by ID
	 * 
	 * @param id primary ID
	 * @return
	 */
	public Application read(UUID orgId, String id)
	{
		if (id.startsWith("name"))
		{
			return readByName(orgId, id.substring(id.indexOf(':') + 1));
		}

		return applications.read(Identifiers.UUID.parse(id));
	}

	public Application readByName(UUID orgId, String name)
	{
		Application application = applications.readByName(orgId, name);
		
		if (application != null)
		{
			return application;
		}

		throw new NotFoundException("Application not found: " + name);
	}

	public List<Application> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
	{
		return applications.readAllWithOutCredentials(filter, range, order);
	}

	public long count(QueryFilter filter)
	{
		return applications.count(filter);
	}

	public Application update(Application application)
	{
		ValidationEngine.validateAndThrow(application);
		return applications.update(application);
	}

	/**
	 * Delete the Application via primary ID
	 * 
	 * @param id primary ID
	 */
	public void delete(UUID orgId, String id)
	{
		applications.delete(read(orgId, id));
	}

	public void verify(String key)
	{
		if (!applications.verifyKey(key))
		{
			throw new InvalidKeyException(key);
		}
	}

	public ApplicationCredentials readCredentials(UUID orgId, String appId)
	{
		Application application = read(orgId, appId);
		return new ApplicationCredentials(application);
	}
	
	public ApplicationCredentials resetCrendentials(UUID orgId, String appId)
	{
		// TODO: Reset named Application credentials (API key)
		Application application = read(orgId, appId);
		application.setApiKey(apiKeys.generateApiKey());
		Application reset = update(application);
		return new ApplicationCredentials(reset);
	}
}
