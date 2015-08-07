/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.tenant;

import com.strategicgains.oauth.domain.AbstractIdentifiableEvent;

/**
 * @author toddf
 * @since Nov 9, 2012
 */
public class TenantDeletedEvent
extends AbstractIdentifiableEvent<Tenant>
{
	public TenantDeletedEvent(Tenant tenant)
	{
		super(tenant);
	}
}
