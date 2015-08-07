/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.query.Query;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.NotFoundException;

import com.mongodb.MongoClient;
import com.strategicgains.oauth.persistence.AbstractBaseMongodbRepository;
import com.strategicgains.repoexpress.domain.Identifier;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class MongodbApplicationRepository
extends AbstractBaseMongodbRepository<Application>
implements ApplicationRepository
{
    @SuppressWarnings("unchecked")
	public MongodbApplicationRepository(MongoClient mongo, String databaseName)
	{
		super(mongo, databaseName, Application.class);
		addObserver(new ApplicationRepositoryObserver(this));
	}

	@Override
	public Application readByName(UUID tenantId, String name)
	{
		return getDataStore().find(Application.class)
			.filter("name =", name)
			.filter("tenant.id =", tenantId)
			.get();
	}

	@Override
	public Application readByClientId(String clientId)
	{
		Application application = getDataStore().find(Application.class, "apiKey.clientId", clientId).get();

		if (application == null)
		{
			throw new NotFoundException("Client ID not found: " + clientId);
		}

		return application;
	}

	@Override
    public boolean verifyKey(String key)
    {
		return (getDataStore().find(Application.class)
			.filter("apiKey.clientId =", key)
			.filter("isActive =", Boolean.TRUE)
			.retrievedFields(true, "apiKey.clientId")
			.countAll() > 0);
    }

    @Override
    public List<Application> readAllWithOutCredentials(QueryFilter filter, QueryRange range, QueryOrder order)
    {
    	Query<Application> q = getBaseQuery(Application.class, filter, range, order);
    	q.retrievedFields(false, "apiKey");
    	return q.asList();
    }

	@Override
	public void deleteAllByTenantId(Identifier tenantId)
	{
		Query<Application> q = getDataStore().find(Application.class, "tenant.id", tenantId.primaryKey());
		getDataStore().delete(q);
	}
}
