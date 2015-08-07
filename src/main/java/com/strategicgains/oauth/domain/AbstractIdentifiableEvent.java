/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.domain;

import com.strategicgains.repoexpress.domain.Identifiable;

/**
 * @author fredta2
 * @since Nov 9, 2012
 */
public abstract class AbstractIdentifiableEvent<T extends Identifiable>
{
	private T identifiable;

	public AbstractIdentifiableEvent(T identifiable)
	{
		this.identifiable = identifiable;
	}

	public T getIdentifiable()
	{
		return identifiable;
	}
}
