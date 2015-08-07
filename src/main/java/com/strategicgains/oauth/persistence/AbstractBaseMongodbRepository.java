/*
 * Copyright 2013, Strategic Gains, Inc.  All rights reserved.
 */
package com.strategicgains.oauth.persistence;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.AbstractUuidMongodbEntity;
import com.strategicgains.repoexpress.mongodb.MongodbUuidEntityRepository;

/**
 * @author toddf
 * @since Feb 8, 2013
 */
public class AbstractBaseMongodbRepository<T extends AbstractUuidMongodbEntity>
extends MongodbUuidEntityRepository<T>
{
	public AbstractBaseMongodbRepository(MongoClient mongo, String databaseName, Class<T>... types)
	{
		super(mongo, databaseName, types);
	}
}
