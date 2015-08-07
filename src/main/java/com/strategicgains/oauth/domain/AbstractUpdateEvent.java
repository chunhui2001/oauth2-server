/*
 * Copyright 2013, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.domain;

import com.strategicgains.repoexpress.domain.Identifiable;

/**
 * @author toddf
 * @since Sep 18, 2013
 */
public class AbstractUpdateEvent<T extends Identifiable>
extends AbstractIdentifiableEvent<T>
{
	private T before;

	public AbstractUpdateEvent(T before, T after)
	{
		super(after);
		this.before = before;
	}
	
	public T getBefore()
	{
		return before;
	}
}
