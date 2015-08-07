package com.strategicgains.outh;

public class Constants
{
	public static final String SERVICE_NAME = "OAuth2 Server";

	/**
	 * These define the URL parameters used in the route definition strings (e.g. '{userId}').
	 */
	public class Url
	{
		//TODO: Your URL parameter names here...
		public static final String SAMPLE_ID = "sampleId";
	}

	/**
	 * These define the route names used in naming each route definitions.  These names are used
	 * to retrieve URL patterns within the controllers by name to create links in responses.
	 */
	public class Routes
	{
		//TODO: Your Route names here...
		public static final String AUTHORIZE = "sample.single.route";
		public static final String TOKEN = "sample.collection.route";
	}
}
