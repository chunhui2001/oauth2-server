package com.strategicgains.oauth;

public class Constants
{
	public static final String SERVICE_NAME = "OAuth2 Server";
	public static final String NAME_PATTERN = "";
	public static final String NAME_MESSAGE = "";

	/**
	 * These define the URL parameters used in the route definition strings (e.g. '{userId}').
	 */
	public class Url
	{
		public static final String API_KEY = "apiKey";
		public static final String TENANT_ID = "tenantId";
		public static final String PARENT_ID = "parentId";
		public static final String APPLICATION_ID = "applicationId";
		public static final String CREDENTIALS_ID = "credentialsId";
	}

	/**
	 * These define the route names used in naming each route definitions.  These names are used
	 * to retrieve URL patterns within the controllers by name to create links in responses.
	 */
	public class Routes
	{
		public static final String AUTHORIZE = "token.authorize";
		public static final String TOKEN = "token.acquisition";

		public static final String TENANT = "tenant.individual";
		public static final String TENANT_COLLECTION = "tenant.collection";
		public static final String TENANT_CREDENTIALS = "tenant.key";
		public static final String TENANT_PARENT = "tenant.parent";

		public static final String APPLICATION = "application.individual";
		public static final String APPLICATION_COLLECTION = "application.collection";
		public static final String APPLICATION_CREDENTIALS = "application.credentials";



		public static final String INDEX_ROUTE = "application.index.route";
	}
}
