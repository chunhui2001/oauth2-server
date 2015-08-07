package com.strategicgains.oauth.tenant;

import static com.strategicgains.oauth.Constants.Url.API_KEY;
import static com.strategicgains.oauth.Constants.Url.PARENT_ID;
import static com.strategicgains.oauth.Constants.Url.TENANT_ID;

import java.util.List;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

import com.strategicgains.oauth.Constants;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.adapter.Identifiers;
import com.strategicgains.repoexpress.util.UuidConverter;

import io.netty.handler.codec.http.HttpMethod;

public class TenantController
{
	private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();

	private TenantService tenants;

	public TenantController(TenantService tenantService)
	{
		super();
		this.tenants = tenantService;
	}

	public Tenant create(Request request, Response response)
	{
		Tenant tenant = request.getBodyAs(Tenant.class, "Tenant details not provided");
		tenant.setUuid(null);
		Tenant saved = tenants.create(tenant);
		saved.setApiKey(null);

		// Construct the response for create...
		response.setResponseCreated();

		// Include the Location header...
		response.addLocationHeader(createLocationHeader(request, saved));
		bindLinkUrls(saved);

		// Return the newly-created Organization...
		return createReadResponse(saved);
	}

	public Tenant read(Request request, Response response)
	{
		String id = request.getHeader(TENANT_ID, "No Tenant ID supplied");
		Tenant tenant = tenants.read(id);
		bindLinkUrls(tenant);
		return createReadResponse(tenant);
	}

	public Tenant readParent(Request request, Response response)
	{
		Tenant child = read(request, response);

		if (child.getParentId() == null)
		{
			return new Tenant();
		}

		Tenant parent = tenants.read(UuidConverter.format(child.getParentId()));
		bindLinkUrls(parent);
		return createReadResponse(parent);
	}

	public List<Tenant> readAll(Request request, Response response)
	{
		QueryFilter filter = QueryFilters.parseFrom(request, "parentId");
		QueryOrder order = QueryOrders.parseFrom(request);
		QueryRange range = QueryRanges.parseFrom(request, 20);
		return query(response, filter, order, range);
	}

	private List<Tenant> query(Response response, QueryFilter filter, QueryOrder order, QueryRange range)
    {
	    List<Tenant> results = tenants.readAll(filter, range, order);
		long count = tenants.count(filter);
		response.setCollectionResponse(range, results.size(), count);

		HyperExpress.tokenBinder(new TokenBinder<Tenant>()
		{
			@Override
			public void bind(Tenant object, TokenResolver r)
			{
				r.bind(TENANT_ID, Identifiers.UUID.format(object.getUuid()));

				if (object.hasParent())
				{
					r.bind(PARENT_ID, Identifiers.UUID.format(object.getParentId()));
				}
			}
		});

		return results;
    }

	public void update(Request request, Response response)
	{
		String id = request.getHeader(TENANT_ID, "No Tenant ID supplied");
		Tenant tenant = request.getBodyAs(Tenant.class, "Tenant details not provided");
		Tenant previous = tenants.read(id);
		tenant.setUuid(previous.getUuid());
		tenant.setApiKey(previous.getApiKey());
		tenant.setSalt(previous.getSalt());
		tenants.update(tenant);
		response.setResponseNoContent();
	}

	public void delete(Request request, Response response)
	{
		String id = request.getHeader(TENANT_ID, "No Tenant ID supplied");
		tenants.delete(id);
		response.setResponseNoContent();
	}

	public TenantCredentials readCredentials(Request request, Response response)
	{
		String id = request.getHeader(TENANT_ID, "No Tenant ID provided");
		TenantCredentials creds = tenants.readCredentials(id);

		HyperExpress
			.bind(TENANT_ID, Identifiers.UUID.format(creds.getTenantId()))
			.bind(API_KEY, creds.getClientId());

		return creds;
	}

	public TenantCredentials resetCredentials(Request request, Response response)
	{
		String id = request.getHeader(TENANT_ID, "No Tenant ID provided");
		TenantCredentials creds = tenants.resetApiKey(id);

		HyperExpress
			.bind(TENANT_ID, Identifiers.UUID.format(creds.getTenantId()))
			.bind(API_KEY, creds.getClientId());

		return creds;
	}

	private String createLocationHeader(Request request, Tenant saved)
	{
		String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.TENANT);
		return LOCATION_BUILDER.build(
		    locationPattern,
		    new DefaultTokenResolver()
		    	.bind(TENANT_ID, Identifiers.UUID.format(saved.getUuid())));
	}

	private void bindLinkUrls(Tenant tenant)
	{
		HyperExpress.bind(TENANT_ID, Identifiers.UUID.format(tenant.getUuid()));

		if (tenant.hasParent())
		{
			HyperExpress.bind(PARENT_ID, Identifiers.UUID.format(tenant.getParentId()));
		}
	}

	private Tenant createReadResponse(Tenant result)
	{
		result.setApiKey(null);
		result.setSalt(null);
		return result;
	}
}
