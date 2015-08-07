/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.application;

import com.strategicgains.oauth.domain.AbstractUpdateEvent;

/**
 * @author toddf
 * @since Feb 8, 2013
 */
public class ApplicationUpdatedEvent
extends AbstractUpdateEvent<Application>
{
	public ApplicationUpdatedEvent(Application before, Application after)
	{
		super(before, after);
	}
}
