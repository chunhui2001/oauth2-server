package com.strategicgains.oauth.serialization;

import org.restexpress.ContentType;
import org.restexpress.Format;
import org.restexpress.common.util.StringUtils;
import org.restexpress.contenttype.MediaTypeParser;
import org.restexpress.serialization.AbstractSerializationProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by keesh on 4/26/17.
 */
public abstract class StaticSerializationProcessor
        extends AbstractSerializationProcessor
{

    public StaticSerializationProcessor(String format, String mediaType)
    {
        super(Arrays.asList(format.split(",")), MediaTypeParser.parse(mediaType));
    }

}