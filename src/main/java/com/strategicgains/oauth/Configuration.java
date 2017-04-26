package com.strategicgains.oauth;

import java.util.Properties;

import com.strategicgains.oauth.controllers.HomeController;
import org.restexpress.RestExpress;
import org.restexpress.common.exception.ConfigurationException;
import org.restexpress.util.Environment;

import com.strategicgains.oauth.application.ApplicationController;
import com.strategicgains.oauth.application.ApplicationRepository;
import com.strategicgains.oauth.application.ApplicationService;
import com.strategicgains.oauth.application.MongodbApplicationRepository;
import com.strategicgains.oauth.domain.ApiKeyGenerator;
import com.strategicgains.oauth.domain.TokenGenerator;
import com.strategicgains.oauth.tenant.MongodbTenantRepository;
import com.strategicgains.oauth.tenant.TenantController;
import com.strategicgains.oauth.tenant.TenantRepository;
import com.strategicgains.oauth.tenant.TenantService;
import com.strategicgains.oauth.token.TokenController;
import com.strategicgains.oauth.token.TokenService;
import com.strategicgains.repoexpress.mongodb.MongoConfig;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

	private static final String DEFAULT_CLIENT_ID_BYTES = "16";
	private static final String DEFAULT_CLIENT_SECRET_BYTES = "32";
	private static final String DEFAULT_SALT_BYTES = "32";
	private static final String DEFAULT_TOKEN_BYTES = "16";
	private static final String DEFAULT_REFRESH_TOKEN_BYTES = "32";

	private static final String CLIENT_ID_BYTES = "crypto.clientId.bytes";
	private static final String CLIENT_SECRET_BYTES = "crypto.clientSecret.bytes";
	private static final String SALT_BYTES = "crypto.salt.bytes";
	private static final String TOKEN_BYTES = "crypto.token.bytes";
	private static final String REFRESH_TOKEN_BYTES = "crypto.refreshToken.bytes";
	private static final String TOKEN_TTL = "crypto.token.ttlSeconds";

	private int port;
	private String baseUrl;
	private int executorThreadPoolSize;
	private int tokenTTL;

	private TokenController tokenController;
	private TenantController tenantController;
	private ApplicationController applicationController;
	private HomeController homeController;

	@Override
	protected void fillValues(Properties p)
	{
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		int clientIdBytes = Integer.parseInt(p.getProperty(CLIENT_ID_BYTES, DEFAULT_CLIENT_ID_BYTES));
		int clientSecretBytes = Integer.parseInt(p.getProperty(CLIENT_SECRET_BYTES, DEFAULT_CLIENT_SECRET_BYTES));
		int tokenBytes = Integer.parseInt(p.getProperty(TOKEN_BYTES, DEFAULT_TOKEN_BYTES));
		int refreshTokenBytes = Integer.parseInt(p.getProperty(REFRESH_TOKEN_BYTES, DEFAULT_REFRESH_TOKEN_BYTES));
		int saltBytes = Integer.parseInt(p.getProperty(SALT_BYTES, DEFAULT_SALT_BYTES));
		this.tokenTTL = Integer.parseInt(p.getProperty(TOKEN_TTL));

		if (this.tokenTTL < 60)
		{
			throw new ConfigurationException(TOKEN_TTL + " must be greater-than 60 (seconds)");
		}

		MongoConfig mongo = new MongoConfig(p);
		initialize(mongo, clientIdBytes, clientSecretBytes, tokenBytes, refreshTokenBytes, saltBytes);
	}

	private void initialize(MongoConfig mongo, int clientIdBytes, int clientSecretBytes, int tokenBytes, int refreshTokenBytes, int saltBytes)
	{
		TenantRepository tenantRepo = new MongodbTenantRepository(mongo.getClient(), mongo.getDbName());
		ApplicationRepository appRepo = new MongodbApplicationRepository(mongo.getClient(), mongo.getDbName());

		ApiKeyGenerator apiKeyGenerator = new ApiKeyGenerator(clientIdBytes, clientSecretBytes, saltBytes);
		TokenGenerator tokenGenerator = new TokenGenerator(tokenBytes, refreshTokenBytes);
		TokenService tokenService = new TokenService(tenantRepo, appRepo, tokenGenerator, saltBytes);

		tokenController = new TokenController(tokenService, null);

		TenantService tenantService = new TenantService(tenantRepo, apiKeyGenerator);
		tenantController = new TenantController(tenantService);

		ApplicationService appService = new ApplicationService(appRepo, apiKeyGenerator);
		applicationController = new ApplicationController(appService);

		homeController = new HomeController();
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

	public HomeController getHomeController() { return homeController; }
}
