/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.eventing.DomainEvents;
import com.strategicgains.repoexpress.event.AbstractRepositoryObserver;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class TenantRepositoryObserver
extends AbstractRepositoryObserver<Tenant>
{
	private TenantRepository tenants;

    public TenantRepositoryObserver(TenantRepository tenantRepo)
    {
	    super();
	    this.tenants = tenantRepo;
    }
    
    @Override
    public void afterCreate(Tenant tenant)
    {
    	DomainEvents.publish(new TenantCreatedEvent(tenant));
    }

	@Override
	public void afterDelete(Tenant tenant)
	{
		DomainEvents.publish(new TenantDeletedEvent(tenant));
	}

	@Override
	public void afterUpdate(Tenant tenant)
	{
		// Cannot update createdAt
		Tenant previous = tenants.read(tenant.getId());
		tenant.setCreatedAt(previous.getCreatedAt());

		// This assumes the update will happen without error...
		DomainEvents.publish(new TenantUpdatedEvent(previous, tenant));
	}
}
