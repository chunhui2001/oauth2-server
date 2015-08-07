/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.oauth.domain.AbstractIdentifiableEvent;

/**
 * @author toddf
 * @since Nov 20, 2012
 */
public class TenantCreatedEvent
extends AbstractIdentifiableEvent<Tenant>
{
	public TenantCreatedEvent(Tenant tenant)
	{
		super(tenant);
	}
}
