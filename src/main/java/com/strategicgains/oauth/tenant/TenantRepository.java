/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import java.util.List;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import com.strategicgains.repoexpress.Queryable;
import com.strategicgains.repoexpress.Repository;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public interface TenantRepository
extends Repository<Tenant>, Queryable<Tenant>
{
	public Tenant readByName(String name);
	public Tenant readByClientId(String clientId);
    public List<Tenant> readAllWithOutApiKey(QueryFilter filter, QueryRange range, QueryOrder order);
}
