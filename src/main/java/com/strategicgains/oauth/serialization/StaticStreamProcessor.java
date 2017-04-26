package com.strategicgains.oauth.serialization;

import com.strategicgains.oauth.domain.ContextResult;
import com.strategicgains.oauth.domain.StaticResource;
import com.strategicgains.oauth.statics.StaticSavedEngine;
import io.netty.buffer.ByteBuf;
import org.restexpress.ContentType;
import org.restexpress.common.util.StringUtils;
import org.restexpress.domain.ErrorResult;
import org.restexpress.exception.NotFoundException;
import org.restexpress.serialization.SerializationException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.UUID;


import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

/**
 * Created by keesh on 4/26/17.
 */
public class StaticStreamProcessor extends StaticSerializationProcessor {


    public StaticStreamProcessor(String format, String mediaType)
    {
        super(format, mediaType);
    }

    private static final byte[] EMPTY_STRING_BYTES = StringUtils.EMPTY_STRING.getBytes(ContentType.CHARSET);

    @Override
    public <T> T deserialize(String string, Class<T> type) {
        return (T) string;
    }

    @Override
    public <T> T deserialize(ByteBuf buffer, Class<T> type) {

        if (!buffer.isReadable()) return null;

        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);

        return (T)new String(req, ContentType.CHARSET);
    }

    @Override
    public ByteBuffer serialize(Object object) {

        if (object == null) return ByteBuffer.wrap(EMPTY_STRING_BYTES);

        if (object instanceof StaticResource) {

            StaticResource staticResource = (StaticResource)object;

            if (!StaticSavedEngine.fileExists(staticResource.getPath())) {
                return ByteBuffer.wrap((new com.google.gson.Gson().toJson(
                        new ErrorResult(UUID.randomUUID(), 404, "404 - Not Found", ""))).getBytes());
            }

            try {
                return StaticSavedEngine.readContent(staticResource.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ByteBuffer.wrap(EMPTY_STRING_BYTES);
        }

        if (object instanceof ContextResult) {

            ContextResult contextResult = (ContextResult)object;

            String realPath = StaticSavedEngine.STATIC_TEMPLATE_DIR + contextResult.getTemplate() ;

            JtwigTemplate template = JtwigTemplate.fileTemplate(realPath);
            JtwigModel model = JtwigModel.newModel(contextResult.getData());
            String htmlContent = template.render(model);

            return ByteBuffer.wrap((htmlContent).getBytes());
        }


        if (object instanceof ErrorResult) {
            return ByteBuffer.wrap((new com.google.gson.Gson().toJson(object)).getBytes());
        }


        return ByteBuffer.wrap(((String)object).getBytes());

    }

}
