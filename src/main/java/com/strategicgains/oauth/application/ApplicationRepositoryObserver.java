/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.eventing.DomainEvents;
import com.strategicgains.repoexpress.event.AbstractRepositoryObserver;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationRepositoryObserver
extends AbstractRepositoryObserver<Application>
{
	private ApplicationRepository applications;

    public ApplicationRepositoryObserver(ApplicationRepository applications)
    {
	    super();
	    this.applications = applications;
    }

	@Override
	public void afterCreate(Application application)
	{
		DomainEvents.publish(new ApplicationCreatedEvent(application));
	}

	@Override
	public void afterDelete(Application application)
	{
		DomainEvents.publish(new ApplicationDeletedEvent(application));
	}

	@Override
	public void beforeUpdate(Application application)
	{
		// Cannot update createdAt
		Application previous = applications.read(application.getId());
		application.setCreatedAt(previous.getCreatedAt());

		// Also cannot update the Application ID
//		application.setApplicationId(previous.getApplicationId());

		// This assumes the update will happen...
		DomainEvents.publish(new ApplicationUpdatedEvent(previous, application));
	}
}
