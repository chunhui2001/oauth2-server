/*
 * Copyright 2012, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth;

import org.restexpress.exception.UnauthorizedException;


/**
 * @author fredta2
 * @since Nov 9, 2012
 */
public class InvalidKeyException
extends UnauthorizedException
{
    private static final long serialVersionUID = 1872263893317573192L;

	public InvalidKeyException()
	{
	}

	/**
	 * @param message
	 */
	public InvalidKeyException(String message)
	{
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidKeyException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidKeyException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
