/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.oauth.domain.AbstractUpdateEvent;

/**
 * @author toddf
 * @since Feb 8, 2013
 */
public class TenantUpdatedEvent
extends AbstractUpdateEvent<Tenant>
{
	public TenantUpdatedEvent(Tenant before, Tenant after)
	{
		super(before, after);
	}
}
