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
package com.strategicgains.oauth.application;

import static com.strategicgains.oauth.Constants.Url.TENANT_ID;
import static com.strategicgains.hyperexpress.RelTypes.UP;

import java.util.List;
import java.util.UUID;

import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;

import com.strategicgains.oauth.tenant.Tenant;
import com.strategicgains.oauth.tenant.TenantService;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.expand.Expansion;
import com.strategicgains.hyperexpress.expand.ExpansionCallback;

/**
 * Expands relationships from a Tenant to others, embedding the related resources as appropriate.
 * 
 * @author toddf
 * @since Aug 8, 2014
 */
public class ApplicationExpansionCallback
implements ExpansionCallback
{
	private ApplicationService applications;
	private TenantService tenants;

	public ApplicationExpansionCallback(ApplicationService applications, TenantService tenants)
    {
	    super();
	    this.applications = applications;
	    this.tenants = tenants;
    }

	@Override
	public Resource expand(Expansion expansion, Resource resource)
	{
		for(String rel : expansion)
		{
			if ("core:apikey".equals(rel))
			{
				expandApiKey(rel, resource, expansion.getMediaType());
			}
			else if (UP.equals(rel))
			{
				expandParent(rel, resource, expansion.getMediaType());
			}
		}

		return resource;
	}

	private void expandApiKey(String rel, Resource resource, String contentType)
    {
	    Resource expansion = HyperExpress.createResource(applications.readCredentials((UUID) resource.getProperty("tenantId"), resource.getProperty("id").toString()), contentType);
	    resource.addResource(rel, expansion);
    }

	private void expandParent(String rel, Resource resource, String contentType)
    {
	    QueryFilter query = new QueryFilter().addCriteria("id", FilterOperator.EQUALS, resource.getProperty(TENANT_ID));
		List<Tenant> parents = tenants.readAll(query, null, null);

	    if (!parents.isEmpty())
	    {
	    	resource.addResource(rel, HyperExpress.createResource(parents.get(0), contentType));
	    }
    }
}
