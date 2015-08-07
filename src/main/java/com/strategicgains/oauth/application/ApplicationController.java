package com.strategicgains.oauth.application;

import static com.strategicgains.oauth.Constants.Url.APPLICATION_ID;
import static com.strategicgains.oauth.Constants.Url.TENANT_ID;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.BadRequestException;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.oauth.Constants;
import com.strategicgains.repoexpress.adapter.Identifiers;
import com.strategicgains.repoexpress.util.UuidConverter;

import io.netty.handler.codec.http.HttpMethod;

public class ApplicationController
//extends AbstractController
{
	private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
	private ApplicationService applications;

	public ApplicationController(ApplicationService applicationService)
	{
		super();
		this.applications = applicationService;
	}

	public Application create(Request request, Response response)
	throws IOException
	{
		Application application = request.getBodyAs(Application.class, "Application details not provided");
		Application saved = applications.create(application);

		// Construct the response for create...
		response.setResponseCreated();

		// Include the Location header...
		response.addLocationHeader(createLocationHeader(saved, request));
		bind(saved);

		return createReadResponse(saved);
	}

	public Application read(Request request, Response response)
	{
		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
		Application result = applications.read(getTenantId(request), appId);
		bind(result);
		return createReadResponse(result);
	}

	public List<Application> readAll(Request request, Response response)
	{
		QueryFilter filter = QueryFilters.parseFrom(request);
		QueryOrder order = QueryOrders.parseFrom(request);
		QueryRange range = QueryRanges.parseFrom(request, 20);
		List<Application> results = applications.readAll(filter, range, order);
		long count = applications.count(filter);
		response.setCollectionResponse(range, results.size(), count);

		HyperExpress.tokenBinder(new TokenBinder<Application>()
		{
			@Override
			public void bind(Application object, TokenResolver tr)
			{
				tr
					.bind(TENANT_ID, Identifiers.UUID.format(object.tenantId()))
					.bind(APPLICATION_ID, Identifiers.UUID.format(object.getUuid()));
			}
		});

		return results;
	}

	public void update(Request request, Response response)
	{
		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
		Application application = request.getBodyAs(Application.class, "Application details not provided");
		Application previous = applications.read(getTenantId(request), appId);

		if (!previous.getId().equals(application.getId()))
		{
			throw new BadRequestException("Cannot change 'id' during update");
		}

		if (!previous.tenantId().equals(application.tenantId()))
		{
			throw new BadRequestException("Cannot change organization during update");
		}

		application.setApiKey(previous.getApiKey());
		application.setSalt(previous.getSalt());
		applications.update(application);
		response.setResponseNoContent();
	}

	public void delete(Request request, Response response)
	{
		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
		applications.delete(getTenantId(request), appId);
		response.setResponseNoContent();
	}

//	public void verifyKey(Request request, Response response)
//	{
//		String key = request.getHeader(API_KEY, "No API key provided");
//		applications.verify(key);
//		response.setResponseNoContent();
//	}

	public ApplicationCredentials readCredentials(Request request, Response response)
	{
		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
		ApplicationCredentials creds = applications.readCredentials(getTenantId(request), appId);

		HyperExpress
			.bind(APPLICATION_ID, Identifiers.UUID.format(creds.getApplicationId()));

		return creds;
	}

//	public ApplicationCredentials addCredentials(Request request, Response response)
//	throws IOException
//	{
//		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
//		ApplicationCredentials apiKey = applications.addCrendentials(getTenantId(request), appId);

//		HyperExpress
//			.bind(APPLICATION_ID, Identifiers.UUID.format(apiKey.getApplicationId()))
//			.bind(CREDENTIALS_ID, credentialsId);
//
//		return null;
//	}

//	public Credentials readSingleCredentials(Request request, Response response)
//	throws IOException
//	{
//		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
//		String credentialsId = request.getHeader(CREDENTIALS_ID, "No Credential ID provided");
//		ApplicationCredentials creds = applications.resetCrendentials(getTenantId(request), appId, credentialsId);
//
//		HyperExpress
//			.bind(APPLICATION_ID, Identifiers.UUID.format(creds.getApplicationId()))
//			.bind(CREDENTIALS_ID, credentialsId);
//
//		return null;
//	}

//	public void deleteCredentials(Request request, Response response)
//	throws IOException
//	{
//		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
//		String credentialsId = request.getHeader(CREDENTIALS_ID, "No Credential ID provided");
//		ApplicationCredentials creds = applications.resetCrendentials(getTenantId(request), appId, credentialsId);
//		response.setResponseNoContent();
//	}

	public ApplicationCredentials resetCredentials(Request request, Response response)
	throws IOException
	{
		String appId = request.getHeader(APPLICATION_ID, "No Application ID provided");
		ApplicationCredentials creds = applications.resetCrendentials(getTenantId(request), appId);

		HyperExpress.bind(APPLICATION_ID, Identifiers.UUID.format(creds.getApplicationId()));

		return creds;
	}

	private Application createReadResponse(Application application)
    {
	    // Don't expose the API key or secret...
		application.setApiKey(null);
		application.setSalt(null);
		return application;
    }

	private String createLocationHeader(Application saved, Request request)
    {
		TokenResolver resolver = HyperExpress.bind(APPLICATION_ID, Identifiers.UUID.format(saved.getUuid()));

		String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.APPLICATION);
		return LOCATION_BUILDER.build(locationPattern, resolver);
    }

	private void bind(Application application)
    {
	    HyperExpress
			.bind(APPLICATION_ID, Identifiers.UUID.format(application.getUuid()))
			.bind(TENANT_ID, Identifiers.UUID.format(application.tenantId()));
    }

	private UUID getTenantId(Request request)
	{
		String tenantIdString = request.getHeader(TENANT_ID, "No Tenant ID provided");
		return UuidConverter.parse(tenantIdString);
	}
}
