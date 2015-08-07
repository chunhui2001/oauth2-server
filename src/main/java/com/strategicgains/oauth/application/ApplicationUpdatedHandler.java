/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.eventing.EventHandler;

/**
 * On Application creation, this handler updates the parent (owning) organization's
 * collection of Application IDs.
 * 
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationUpdatedHandler
implements EventHandler
{
	@Override
	public void handle(Object message)
	throws Exception
	{
		ApplicationUpdatedEvent event = (ApplicationUpdatedEvent) message;

		// If the application ID changed, need to update organization.
		if (isApplicationIdChanged(event))
		{
//			Organization organization = organizations.read(event.getIdentifiable().getOrganizationId().toString());
//			organization.removeApplicationId(event.getBefore().getSlug());
//			organization.addApplicationId(event.getIdentifiable().getSlug());
//			organizations.update(organization);
		}
	}

	@Override
	public boolean handles(Class<?> eventType)
	{
		return ApplicationUpdatedEvent.class.isAssignableFrom(eventType);
	}

	/**
	 * @param event
	 * @return
	 */
    private boolean isApplicationIdChanged(ApplicationUpdatedEvent event)
    {
	    return (!event.getBefore().getName().equals(event.getIdentifiable().getName()));
    }
}
