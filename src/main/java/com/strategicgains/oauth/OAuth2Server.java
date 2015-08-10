/*
    Copyright 2015, Strategic Gains, Inc.

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

import org.restexpress.RestExpress;
import org.restexpress.exception.UnauthorizedException;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;

import com.strategicgains.oauth.serialization.SerializationProvider;

/**
 * @author tfredrich
 * @since Aug 7, 2015
 */
public class OAuth2Server
{
//	private static final Logger LOG = LoggerFactory.getLogger(Constants.SERVICE_NAME);

	private RestExpress server;
	private Configuration config;
	boolean isStarted = false;

	public OAuth2Server(Configuration config)
	{
		super();
		this.config = config;
		RestExpress.setDefaultSerializationProvider(new SerializationProvider());
		server = new RestExpress()
				.setName(Constants.SERVICE_NAME)
				.setBaseUrl(config.getBaseUrl())
				.setExecutorThreadCount(config.getExecutorThreadPoolSize())
				.addMessageObserver(new SimpleConsoleLogMessageObserver());

		server.mapException(Oauth2Exception.class, UnauthorizedException.class);

		Routes.define(config, server);
	}

	public OAuth2Server start()
    {
		if (!isStarted)
		{
			server.bind(config.getPort());			
		}

	    return this;
    }

	public void awaitShutdown()
    {
		if (isStarted) server.awaitShutdown();
    }

	public void shutdown()
    {
		if (isStarted) server.shutdown();
    }
}
