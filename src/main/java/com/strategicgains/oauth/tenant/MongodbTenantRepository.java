/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.NotFoundException;

import com.mongodb.MongoClient;
import com.strategicgains.oauth.persistence.AbstractBaseMongodbRepository;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class MongodbTenantRepository
extends AbstractBaseMongodbRepository<Tenant>
implements TenantRepository
{
    @SuppressWarnings("unchecked")
	public MongodbTenantRepository(MongoClient mongo, String databaseName)
	{
		super(mongo, databaseName, Tenant.class);
		addObserver(new TenantRepositoryObserver(this));
	}

	@Override
	public Tenant readByName(String name)
	{
		return getDataStore().find(Tenant.class, "name", name).get();
	}

	@Override
	public Tenant readByClientId(String clientId)
	{
		Tenant organization = getDataStore().find(Tenant.class, "apiKey.clientId", clientId).get();

		if (organization == null)
		{
			throw new NotFoundException("Client ID not found: " + clientId);
		}

		return organization;
	}

    @Override
    public List<Tenant> readAllWithOutApiKey(QueryFilter filter, QueryRange range, QueryOrder order)
    {
    	Query<Tenant> q = getBaseQuery(Tenant.class, filter, range, order);
    	q.retrievedFields(false, "apiKey", "salt");
    	return q.asList();
    }
}
