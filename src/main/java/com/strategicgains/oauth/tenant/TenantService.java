/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import java.util.List;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;

import com.strategicgains.oauth.domain.ApiKeyGenerator;
import com.strategicgains.repoexpress.adapter.Identifiers;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 * @author toddf
 * @since Nov 12, 2012
 */
public class TenantService
{
	private TenantRepository tenants;
	private ApiKeyGenerator keyGenerator;

	public TenantService(TenantRepository tenantRepo, ApiKeyGenerator apiKeyGenerator)
	{
		super();
		this.tenants = tenantRepo;
		this.keyGenerator = apiKeyGenerator;
	}

	public Tenant create(Tenant tenant)
	{
		// Ensure the tenant name is unique.
		if (tenants.readByName(tenant.getName()) != null)
		{
			throw new ConflictException("Tenant name already taken: " + tenant.getName());
		}

		tenant.setApiKey(keyGenerator.generateApiKey());
		tenant.setSalt(keyGenerator.generateSalt());
		ValidationEngine.validateAndThrow(tenant);
		return tenants.create(tenant);
	}

	/**
	 * Read a tenant by ID.
	 * 
	 * @param id the ID of the tenant.
	 * @return
	 */
	public Tenant read(String id)
	{
		if (id.startsWith("name"))
		{
			return readByName(id.substring(id.indexOf(':') + 1));
		}

		return tenants.read(Identifiers.UUID.parse(id));
	}

	public Tenant readByName(String name)
	{
		Tenant organization = tenants.readByName(name);

		if (organization != null)
		{
			return organization;
		}
		
		throw new NotFoundException("Tenant not found. Name: " + name);
	}

	public List<Tenant> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
	{
		return tenants.readAllWithOutApiKey(filter, range, order);
	}

	public long count(QueryFilter filter)
	{
		return tenants.count(filter);
	}

	public void update(Tenant tenant)
	{
		Tenant previous = tenants.read(tenant.getId());
		tenant.setApiKey(previous.getApiKey());
		tenant.setSalt(previous.getSalt());
		ValidationEngine.validateAndThrow(tenant);
		tenants.update(tenant);
	}

	public TenantCredentials readCredentials(String id)
	{
		Tenant tenant = read(id);
		return new TenantCredentials(tenant);
	}

	public TenantCredentials resetApiKey(String id)
	{
		Tenant tenant = read(id);
		tenant.setApiKey(keyGenerator.generateApiKey());
		Tenant reset = tenants.update(tenant);
		return new TenantCredentials(reset);
	}

	public void delete(String id)
	{
		tenants.delete(read(id));
	}
}
