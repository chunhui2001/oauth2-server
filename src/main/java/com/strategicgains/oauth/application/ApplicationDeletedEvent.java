/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.oauth.domain.AbstractIdentifiableEvent;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class ApplicationDeletedEvent
extends AbstractIdentifiableEvent<Application>
{
	public ApplicationDeletedEvent(Application application)
	{
		super(application);
	}
}
