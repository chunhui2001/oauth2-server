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
package com.strategicgains.oauth.domain;

import java.util.UUID;

import com.strategicgains.repoexpress.domain.UuidIdentifiable;

/**
 * @author toddf
 * @since Dec 19, 2014
 */
public class Reference<T extends UuidIdentifiable>
{
	private UUID id;

	public Reference(T referred)
	{
		this(referred.getUuid());
	}

	public Reference(UUID uuid)
	{
		super();
		id(uuid);
	}

	public UUID id()
	{
		return id;
	}

	protected void id(UUID id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		int code = 1043;
		if (id != null) code += id.hashCode();
		return code;
	}

	@Override
	public boolean equals(Object that)
	{
		if (that == null) return false;

		return (this.hashCode() == that.hashCode());
	}
}
