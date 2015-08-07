/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.eventing.EventHandler;

/**
 * On organization creation, this handler creates authorization principles and/or resources
 * in the external authorization system.
 * 
 * @author toddf
 * @since Nov 20, 2012
 */
public class TenantCreatedHandler
implements EventHandler
{
    public TenantCreatedHandler()
    {
	    super();
    }

	@Override
	public void handle(Object object)
	throws Exception
	{
		TenantCreatedEvent event = (TenantCreatedEvent) object;
		// TODO: Create authorization principle(s) and/or Resource(s)
	}

	@Override
	public boolean handles(Class<?> eventType)
	{
		return TenantCreatedEvent.class.isAssignableFrom(eventType);
	}
}
