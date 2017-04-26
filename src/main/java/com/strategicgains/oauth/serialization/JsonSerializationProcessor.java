package com.strategicgains.oauth.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.strategicgains.oauth.domain.ContextResult;
import com.strategicgains.util.date.DateAdapterConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import org.restexpress.ContentType;
import org.restexpress.Format;
import org.restexpress.common.util.StringUtils;
import org.restexpress.serialization.DeserializationException;
import org.restexpress.serialization.SerializationException;
import org.restexpress.serialization.json.JacksonEncodingStringSerializer;
import org.restexpress.serialization.json.JacksonJsonProcessor;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.restexpress.serialization.json.JacksonTimepointDeserializer;
import org.restexpress.serialization.json.JacksonTimepointSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonSerializationProcessor
extends org.restexpress.serialization.json.JsonSerializationProcessor
{
    private static final byte[] EMPTY_STRING_BYTES = StringUtils.EMPTY_STRING.getBytes(ContentType.CHARSET);
    private ObjectMapper mapper;
    private boolean shouldOutboundEncode;

    public JsonSerializationProcessor()
    {
        this(true);
    }

    public JsonSerializationProcessor(boolean shouldOutboundEncode)
    {
        this(Format.JSON, shouldOutboundEncode);
    }

    public JsonSerializationProcessor(String format)
    {
        this(format, true);
    }

    public JsonSerializationProcessor(String format, boolean shouldOutboundEncode)
    {
        super(format);
        this.shouldOutboundEncode = shouldOutboundEncode;
        SimpleModule module = new SimpleModule();
        initializeModule(module);
    }

    public JsonSerializationProcessor(SimpleModule module)
    {
        initialize(module);
    }

    public JsonSerializationProcessor(ObjectMapper mapper)
    {
        super();
        this.mapper = mapper;
    }

    private void initialize(SimpleModule module)
    {
        this.mapper = new ObjectMapper();
        mapper.registerModule(module);
        initializeMapper(mapper);
    }

    /**
     * Template method for sub-classes to augment the module with desired
     * serializers and/or deserializers.  Sub-classes should call super()
     * to get default settings.
     *
     * @param module a SimpleModule
     */
    protected void initializeModule(SimpleModule module)
    {
        module
                .addSerializer(Date.class, new JacksonTimepointSerializer())
                .addDeserializer(Date.class, new JacksonTimepointDeserializer());

        if (shouldOutboundEncode)
        {
            module.addSerializer(String.class, new JacksonEncodingStringSerializer());
        }

        initialize(module);
    }

    /**
     * Template method for sub-classes to augment the mapper with desired
     * settings.  Sub-classes should call super() to get default settings.
     *
     * @param mapper a SimpleModule
     */
    protected void initializeMapper(ObjectMapper mapper)
    {
        mapper
//			.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)

                        // Ignore additional/unknown properties in a payload.
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                        // Only serialize populated properties (do no serialize nulls)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)

                        // Use fields directly.
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)

                        // Ignore accessor and mutator methods (use fields per above).
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)

                        // Set default date output format.
                .setDateFormat(new SimpleDateFormat(DateAdapterConstants.TIME_POINT_OUTPUT_FORMAT));
    }

    @Override
    public <T> T deserialize(String string, Class<T> type)
    {
        try
        {
            return (string == null || string.trim().isEmpty() ? null : mapper.readValue(string, type));
        }
        catch (JsonProcessingException e)
        {
            throw new DeserializationException(e);
        }
        catch (IOException e)
        {
            throw new DeserializationException(e);
        }
    }

    @Override
    public <T> T deserialize(ByteBuf buffer, Class<T> type)
    {
        try
        {

            return (buffer == null || buffer.readableBytes() == 0 ? null : mapper.readValue(new InputStreamReader(new ByteBufInputStream(buffer), ContentType.CHARSET), type));
        }
        catch (JsonProcessingException e)
        {
            throw new DeserializationException(e);
        }
        catch (IOException e)
        {
            throw new DeserializationException(e);
        }
    }

    @Override
    public ByteBuffer serialize(Object object)
    {
        try
        {
            if (object == null) return ByteBuffer.wrap(EMPTY_STRING_BYTES);


            if (object instanceof ContextResult) {
                System.out.println( " JsonSerializationProcessor serialize 2");
                ContextResult result = (ContextResult)object;
                result.setTemplate(null);
                object = result;
            }

            ByteArrayOutputStream b = new ByteArrayOutputStream();

            mapper.writeValue(b, object);

            return ByteBuffer.wrap(b.toByteArray());
        }
        catch (IOException e)
        {
            throw new SerializationException(e);
        }
    }

}
