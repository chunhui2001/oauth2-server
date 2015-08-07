package com.strategicgains.oauth;

import java.util.Properties;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import com.strategicgains.oauth.application.ApplicationController;
import com.strategicgains.oauth.application.ApplicationRepository;
import com.strategicgains.oauth.application.ApplicationService;
import com.strategicgains.oauth.application.MongodbApplicationRepository;
import com.strategicgains.oauth.tenant.MongodbTenantRepository;
import com.strategicgains.oauth.tenant.TenantController;
import com.strategicgains.oauth.tenant.TenantRepository;
import com.strategicgains.oauth.tenant.TenantService;
import com.strategicgains.oauth.token.TokenController;
import com.strategicgains.repoexpress.mongodb.MongoConfig;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

	private int port;
	private String baseUrl;
	private int executorThreadPoolSize;

	private TokenController tokenController;
	private TenantController tenantController;
	private ApplicationController applicationController;

	@Override
	protected void fillValues(Properties p)
	{
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		MongoConfig mongo = new MongoConfig(p);
		initialize(mongo);
	}

	private void initialize(MongoConfig mongo)
	{
		tokenController = new TokenController(null);

		TenantRepository tenantRepo = new MongodbTenantRepository(mongo.getClient(), mongo.getDbName());
		TenantService tenantService = new TenantService(tenantRepo, apiKeyGenerator);
		tenantController = new TenantController(tenantService);

		ApplicationRepository appRepo = new MongodbApplicationRepository(mongo.getClient(), mongo.getDbName());
		ApplicationService appService = new ApplicationService(appRepo, apiKeyGenerator);
		applicationController = new ApplicationController(appService);
	}

	public int getPort()
	{
		return port;
	}
	
	public String getBaseUrl()
	{
		return baseUrl;
	}
	
	public int getExecutorThreadPoolSize()
	{
		return executorThreadPoolSize;
	}

	public TokenController getTokenController()
	{
		return tokenController;
	}

	public TenantController getTenantController()
	{
		return tenantController;
	}

	public ApplicationController getApplicationController()
	{
		return applicationController;
	}
}
