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
package com.strategicgains.outh.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.restexpress.ContentType;
import org.restexpress.Request;

import com.strategicgains.util.date.DateAdapter;
import com.strategicgains.util.date.HttpHeaderTimestampAdapter;

/**
 * Wraps a RestExpress request, implementing the HttpServletRequest with it.
 *  
 * @author toddf
 * @since Nov 21, 2014
 */
public class RequestWrapper
implements HttpServletRequest
{
	private Request request;
	private Map<String, String> parameters;

	public RequestWrapper(Request request)
	{
		super();
		this.request = request;
	}

	@Override
	public Object getAttribute(String name)
	{
		return request.getAttachment(name);
	}

	@Override
	public Enumeration getAttributeNames()
	{
		return Collections.emptyEnumeration();
	}

	@Override
	public String getCharacterEncoding()
	{
		return ContentType.ENCODING;
	}

	@Override
	public void setCharacterEncoding(String env)
	throws UnsupportedEncodingException
	{
	}

	@Override
	public int getContentLength()
	{
//		return request.getContentLength();
		return 0;
	}

	@Override
	public String getContentType()
	{
		return request.getMediaType();
	}

	@Override
	public ServletInputStream getInputStream()
	throws IOException
	{
		return (ServletInputStream) request.getBodyAsStream();
	}

	@Override
	public String getParameter(String name)
	{
		if (parameters == null)
		{
			parameters = request.getQueryStringMap();

			if (request.getMediaType().startsWith("application/x-www-form-urlencoded"))
			{
				for (Entry<String, List<String>> entry : request.getBodyFromUrlFormEncoded().entrySet())
				{
					parameters.put(entry.getKey(), entry.getValue().get(0));
				}
			}
		}

		return parameters.get(name);
	}

	@Override
	public Enumeration getParameterNames()
	{
		return Collections.enumeration(request.getQueryStringMap().keySet());
	}

	@Override
	public String[] getParameterValues(String name)
	{
		return request.getQueryStringMap().values().toArray(new String[0]);
	}

	@Override
	public Map getParameterMap()
	{
		return request.getQueryStringMap();
	}

	@Override
	public String getProtocol()
	{
		return request.getProtocol();
	}

	@Override
	public String getScheme()
	{
		return request.getProtocol();
	}

	@Override
	public String getServerName()
	{
		String host = request.getHost();
		int i = host.indexOf(':');

		return (i > 0 ? host.substring(0, i) : host);
	}

	@Override
	public int getServerPort()
	{
		String host = request.getHost();
		int i = host.indexOf(':');

		if (i > 0)
		{
			return Integer.parseInt(host.substring(i));
		}

		return 80;
	}

	@Override
	public BufferedReader getReader() throws IOException
	{
		return new BufferedReader(new InputStreamReader(request.getBodyAsStream(), ContentType.CHARSET));
	}

	@Override
	public String getRemoteAddr()
	{
		return request.getRemoteAddress().toString();
	}

	@Override
	public String getRemoteHost()
	{
		return request.getRemoteAddress().getHostString();
	}

	@Override
	public void setAttribute(String name, Object o)
	{
		request.putAttachment(name, o);
	}

	@Override
	public void removeAttribute(String name)
	{
	}

	@Override
	public Locale getLocale()
	{
//		return request.getLocale();
		return Locale.US;
	}

	@Override
	public Enumeration getLocales()
	{
//		return Collections.enumeration(request.getLocales());
		return Collections.enumeration(Arrays.asList(getLocale()));
	}

	@Override
	public boolean isSecure()
	{
		return request.getProtocol().endsWith("s");
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path)
	{
		return null;
	}

	@Override
	@Deprecated
	public String getRealPath(String path)
	{
		return null;
	}

	@Override
	public int getRemotePort()
	{
		return request.getRemoteAddress().getPort();
	}

	@Override
	public String getLocalName()
	{
		return "";
	}

	@Override
	public String getLocalAddr()
	{
		return "";
	}

	@Override
	public int getLocalPort()
	{
		return 0;
	}

	@Override
	public String getAuthType()
	{
		return null;
	}

	@Override
	public Cookie[] getCookies()
	{
		return null;
	}

	@Override
	public long getDateHeader(String name)
	{
		return 0;
	}

	@Override
	public String getHeader(String name)
	{
		String header = request.getHeader(name);

		if (header == null) return "-1";
		
		DateAdapter d = new HttpHeaderTimestampAdapter();
		try
        {
	        return String.valueOf(d.parse(header).getTime());
        }
        catch (ParseException e)
        {
        	throw new IllegalArgumentException(header);
        }
	}

	@Override
	public Enumeration getHeaders(String name)
	{
		return Collections.enumeration(request.getHeaders(name));
	}

	@Override
	public Enumeration getHeaderNames()
	{
		return Collections.enumeration(request.getHeaderNames());
	}

	@Override
	public int getIntHeader(String name)
	{
		String header = request.getHeader(name);

		if (header == null) return -1;

        return Integer.parseInt(header);
	}

	@Override
	public String getMethod()
	{
		return request.getHttpMethod().getName();
	}

	@Override
	public String getPathInfo()
	{
		return request.getPath();
	}

	@Override
	public String getPathTranslated()
	{
		return request.getPath();
	}

	@Override
	public String getContextPath()
	{
		String path = request.getPath();
		return (path.endsWith("/") ? path.substring(0, path.length() - 1) : path);
	}

	@Override
	public String getQueryString()
	{
		int i = request.getUrl().indexOf('?');
		return (i > 0 ? request.getUrl().substring(i - 1) : request.getUrl());
	}

	@Override
	public String getRemoteUser()
	{
		return null;
	}

	@Override
	public boolean isUserInRole(String role)
	{
		return false;
	}

	@Override
	public Principal getUserPrincipal()
	{
		return null;
	}

	@Override
	public String getRequestedSessionId()
	{
		return null;
	}

	@Override
	public String getRequestURI()
	{
		int i = request.getUrl().indexOf('?');
		return (i > 0 ? request.getUrl().substring(0, i - 1) : request.getUrl());
	}

	@Override
	public StringBuffer getRequestURL()
	{
		return new StringBuffer(getRequestURI());
	}

	@Override
	public String getServletPath()
	{
		return request.getPath();
	}

	@Override
	public HttpSession getSession(boolean create)
	{
		return null;
	}

	@Override
	public HttpSession getSession()
	{
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL()
	{
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl()
	{
		return false;
	}
}
