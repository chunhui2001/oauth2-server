package com.strategicgains.oauth.tenant;

import java.util.Map;
import java.util.UUID;

import javax.xml.stream.Location;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;

import com.strategicgains.oauth.Constants;
import com.strategicgains.oauth.domain.AbstractEntity;
import com.strategicgains.oauth.domain.ApiKey;
import com.strategicgains.oauth.domain.Reference;
import com.strategicgains.syntaxe.annotation.IntegerValidation;
import com.strategicgains.syntaxe.annotation.ObjectValidation;
import com.strategicgains.syntaxe.annotation.RegexValidation;
import com.strategicgains.syntaxe.annotation.Required;

/**
 * @author toddf
 * @since Oct 10, 2012
 */
@Entity("tenants")
@Indexes(
{
	@Index(name = "tenant.apiKey", value = "apiKey.clientId", unique = true)
})
@ObjectValidation(TenantValidator.class)
public class Tenant
extends AbstractEntity
{
	// Parent tenant ID (null if root tenant)
	private Reference<Tenant> parent;

	@Indexed(unique = true)
	@RegexValidation(name = "Tenant Name", nullable = false, pattern = Constants.NAME_PATTERN, message = Constants.NAME_MESSAGE)
	private String name;

	private String description;

	// Overrides the default (system-wide) TTL in seconds for tokens.
	@IntegerValidation(name = "Token TTL (seconds)", min=0)
	private int tokenTTL;

	// Expansion attributes.
	private Map<String, String> extensions;
	private Location location;

	@Required("API Key")
	private ApiKey apiKey;

	@Required("Security Salt")
	private String salt;

	/**
	 * @return the organization name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the organization name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setApiKey(ApiKey apiKey)
	{
		this.apiKey = (apiKey == null ? null : new ApiKey(apiKey));
	}

	public ApiKey getApiKey()
	{
		return (apiKey == null ? null : new ApiKey(apiKey));
	}

	/**
	 * @return the apiKey clientId
	 */
	public String getClientId()
	{
		return (apiKey == null ? null : apiKey.getClientId());
	}

	/**
	 * @return the apiKey secret
	 */
	public String getSecret()
	{
		return (apiKey == null ? null : apiKey.getClientSecret());
	}

	/**
	 * Get the Tenant's security salt.
	 * 
	 * @return the security salt.
	 */
	public String getSalt()
	{
		return salt;
	}

	/**
	 * Note that if the salt gets changed, none of the previously-stored
	 * security values (e.g. API key) will be able to be verified.
	 * 
	 * @param securitySalt the new security salt.
	 */
	public void setSalt(String securitySalt)
	{
		this.salt = securitySalt;
	}

	public Reference<Tenant> parent()
	{
		return parent;
	}

	public boolean hasParent()
	{
		return (parent != null);
	}

	public void parent(Tenant tenant)
	{
		this.parent = new Reference<Tenant>(tenant);
	}

	public UUID getParentId()
	{
		return (hasParent() ? parent.id() : null );
	}

	public void parentId(UUID parentId)
	{
		this.parent = new Reference<Tenant>(parentId);
	}

	public int getTokenTTL()
	{
		return tokenTTL;
	}

	public boolean hasTokenTTL()
	{
		return tokenTTL != 0;
	}

	public void setTokenTTL(int tokenTTL)
	{
		this.tokenTTL = tokenTTL;
	}

	@Override
	public int hashCode()
	{
		int result = 31;
		result +=  ((apiKey == null) ? 0 : apiKey.hashCode());
		result += ((description == null) ? 0 : description.hashCode());
		result += ((extensions == null) ? 0 : extensions.hashCode());
		result += ((location == null) ? 0 : location.hashCode());
		result += ((name == null) ? 0 : name.hashCode());
		result += ((parent == null) ? 0 : parent.hashCode());
		result += tokenTTL;
		return result;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that) return true;
		if (that == null) return false;
		if (getClass() != that.getClass()) return false;
		return equals((Tenant) that);
	}

	public boolean equals(Tenant that)
	{
		if (apiKey == null)
		{
			if (that.apiKey != null) return false;
		}
		else if (!apiKey.equals(that.apiKey)) return false;
		if (description == null)
		{
			if (that.description != null) return false;
		}
		else if (!description.equals(that.description)) return false;
		if (extensions == null)
		{
			if (that.extensions != null) return false;
		}
		else if (!extensions.equals(that.extensions)) return false;
		if (location == null)
		{
			if (that.location != null) return false;
		}
		else if (!location.equals(that.location)) return false;
		if (name == null)
		{
			if (that.name != null) return false;
		}
		else if (!name.equals(that.name)) return false;
		if (parent == null)
		{
			if (that.parent != null) return false;
		}
		else if (!parent.equals(that.parent)) return false;
		if (tokenTTL != that.tokenTTL) return false;
		return true;
	}
}