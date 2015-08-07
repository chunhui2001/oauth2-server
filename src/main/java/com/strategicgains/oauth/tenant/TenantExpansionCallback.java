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
package com.strategicgains.oauth.tenant;

import static com.strategicgains.oauth.Constants.Url.TENANT_ID;

import java.util.Collections;
import java.util.List;

import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.Resource;
import com.strategicgains.hyperexpress.expand.Expansion;
import com.strategicgains.hyperexpress.expand.ExpansionCallback;
import com.strategicgains.oauth.application.Application;
import com.strategicgains.oauth.application.ApplicationService;

/**
 * Expands relationships from a Tenant to others, embedding the related resources as appropriate.
 * 
 * @author toddf
 * @since Aug 8, 2014
 */
public class TenantExpansionCallback
implements ExpansionCallback
{
	private TenantService tenants;
	private ApplicationService applications;

	public TenantExpansionCallback(TenantService tenants, ApplicationService applications)
    {
	    super();
	    this.tenants = tenants;
	    this.applications = applications;
    }

	@Override
	public Resource expand(Expansion expansion, Resource resource)
	{
		for(String rel : expansion)
		{
			if ("tenant:apikey".equals(rel))
			{
				expandApiKey(rel, resource, expansion.getMediaType());
			}
			else if ("tenant:applications".equals(rel))
			{
				expandApplications(rel, resource, expansion.getMediaType());
			}
			else if ("tenant:children".equals(rel))
			{
				expandChildren(rel, resource, expansion.getMediaType());
			}
		}

		return resource;
	}

	private void expandApiKey(String rel, Resource resource, String contentType)
    {
	    Resource expansion = HyperExpress.createResource(tenants.readCredentials(resource.getProperty("id").toString()), contentType);
	    resource.addResource(rel, expansion);
    }

	private void expandApplications(String rel, Resource resource, String contentType)
    {
	    QueryFilter query = new QueryFilter().addCriteria(TENANT_ID, FilterOperator.EQUALS, resource.getProperty("id"));
	    List<Application> apps = applications.readAll(query, null, null);

	    if (apps.isEmpty())
	    {
	    	resource.addResources(rel, Collections.<Resource> emptyList());
	    }
	    else
	    {
	    	Resource embedded = null;

	    	for (Application app : apps)
	    	{
	    		embedded = HyperExpress.createResource(app, contentType);
	    		resource.addResource("tenant:applications", embedded, true);
	    	}
	    }
    }

	private void expandChildren(String rel, Resource resource, String contentType)
    {
	    QueryFilter query = new QueryFilter().addCriteria("parentId", FilterOperator.EQUALS, resource.getProperty("id"));
	    List<Tenant> children = tenants.readAll(query, null, null);
	    
	    if (children.isEmpty())
	    {
	    	resource.addResources(rel, Collections.<Resource> emptyList());
	    }
	    else
	    {
	    	Resource embedded = null;

	    	for (Tenant child : children)
	    	{
	    		embedded = HyperExpress.createResource(child, contentType);
	    		resource.addResource(rel, embedded, true);
	    	}
	    }
    }
}
