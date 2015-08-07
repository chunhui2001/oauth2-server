/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.eventing.EventHandler;
import com.strategicgains.oauth.application.ApplicationRepository;

/**
 * On organization deletion, this handler cascade-deletes all the child applications.
 * It also deletes principles and resources from the external authorization system.
 * 
 * @author toddf
 * @since Nov 9, 2012
 */
public class TenantDeletedHandler
implements EventHandler
{
	private ApplicationRepository applications;

    public TenantDeletedHandler(ApplicationRepository applications)
    {
	    super();
	    this.applications = applications;
    }

	@Override
	public void handle(Object object)
	throws Exception
	{
		TenantDeletedEvent event = (TenantDeletedEvent) object;
		applications.deleteAllByTenantId(event.getIdentifiable().getId());
		// TODO: remove the principle(s) and/or resource(s) in authorization.
	}

	@Override
	public boolean handles(Class<?> eventType)
	{
		return TenantDeletedEvent.class.isAssignableFrom(eventType);
	}
}
