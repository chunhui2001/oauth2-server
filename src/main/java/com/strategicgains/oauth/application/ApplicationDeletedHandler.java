/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.eventing.EventHandler;
import com.strategicgains.oauth.tenant.TenantRepository;

/**
 * On Application deletion, this handler updates the parent (owning) organization's
 * collection of Application IDs, removing the deleted application ID from the
 * collection.
 * 
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationDeletedHandler
implements EventHandler
{
	private TenantRepository tenants;

	public ApplicationDeletedHandler(TenantRepository tenantRepo)
	{
		super();
		this.tenants = tenantRepo;
	}
	@Override
	public void handle(Object object)
	throws Exception
	{
		ApplicationDeletedEvent event = (ApplicationDeletedEvent) object;
//		Tenant organization = tenants.read(new Identifier(event.getIdentifiable().getTenantId()));
//		organization.removeApplicationId(event.getIdentifiable().getUuid());
//		tenants.update(organization);
	}

	@Override
	public boolean handles(Class<?> eventType)
	{
		return ApplicationDeletedEvent.class.isAssignableFrom(eventType);
	}
}
