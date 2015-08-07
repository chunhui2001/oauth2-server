/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.eventing.EventHandler;
import com.strategicgains.oauth.tenant.TenantRepository;

/**
 * On Application creation, this handler updates the parent (owning) Organization's
 * collection of Application IDs.
 * 
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationCreatedHandler
implements EventHandler
{
	private TenantRepository tenants;

	public ApplicationCreatedHandler(TenantRepository tenantRepo)
	{
		super();
		this.tenants = tenantRepo;
	}

	@Override
	public void handle(Object object)
	throws Exception
	{
		ApplicationCreatedEvent event = (ApplicationCreatedEvent) object;
//		Tenant organization = tenants.read(new Identifier(event.getIdentifiable().getTenantId()));
//		organization.addApplicationId(event.getIdentifiable().getUuid());
//		tenants.update(organization);
	}

	@Override
	public boolean handles(Class<?> eventType)
	{
		return ApplicationCreatedEvent.class.isAssignableFrom(eventType);
	}
}
