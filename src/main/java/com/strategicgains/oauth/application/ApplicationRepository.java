/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import java.util.List;
import java.util.UUID;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import com.strategicgains.repoexpress.Queryable;
import com.strategicgains.repoexpress.Repository;
import com.strategicgains.repoexpress.domain.Identifier;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public interface ApplicationRepository
extends Repository<Application>, Queryable<Application>
{
	public boolean verifyKey(String key);
    public Application readByName(UUID organizationId, String name);
    public Application readByClientId(String clientId);
    public List<Application> readAllWithOutCredentials(QueryFilter filter, QueryRange range, QueryOrder order);
	public void deleteAllByTenantId(Identifier organizationId);
}
