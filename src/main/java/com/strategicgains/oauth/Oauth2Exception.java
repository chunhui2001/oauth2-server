/*
    Copyright 2014, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package com.strategicgains.oauth;

/**
 * @author toddf
 * @since May 4, 2014
 */
public class Oauth2Exception
extends RuntimeException
{
	private static final long serialVersionUID = -7406206170957357446L;

	public Oauth2Exception()
	{
		super();
	}

	public Oauth2Exception(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Oauth2Exception(String message, Throwable cause)
	{
		super(message, cause);
	}

	public Oauth2Exception(String message)
	{
		super(message);
	}

	public Oauth2Exception(Throwable cause)
	{
		super(cause);
	}
}
