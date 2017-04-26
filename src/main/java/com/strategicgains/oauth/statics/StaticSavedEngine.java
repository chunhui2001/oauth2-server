package com.strategicgains.oauth.statics;

import com.strategicgains.oauth.serialization.SerializationProvider;
import com.strategicgains.oauth.serialization.StaticStreamProcessor;
import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;
import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.response.ResponseWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.strategicgains.oauth.Constants.Routes.TENANT_PARENT;
import static io.netty.handler.codec.http.HttpMethod.GET;

/**
 * Created by keesh on 4/26/17.
 */
public class StaticSavedEngine {

    public static final String STATIC_ROOT_DIR =  System.getProperty("java.class.path") + "/statics";
    public static final String STATIC_TEMPLATE_DIR =  System.getProperty("java.class.path") + "/templates";

    public static void enable(RestExpress server, SerializationProvider serializationProvider) {

        serializationProvider.add(new StaticStreamProcessor("ico", "image/x-icon"), SerializationProvider.wrapper(), false);
        serializationProvider.add(new StaticStreamProcessor("jpeg", "image/jpeg"), SerializationProvider.wrapper(), false);
        serializationProvider.add(new StaticStreamProcessor("html", "text/html"), SerializationProvider.wrapper(), false);
        serializationProvider.add(new StaticStreamProcessor("css", "text/css"), SerializationProvider.wrapper(), false);

        File staticRoot = new File(STATIC_ROOT_DIR);

        if (!(staticRoot).exists()) {
            return;
        }

        List<File> fileList = listf(STATIC_ROOT_DIR);

        if (fileList.size() == 0) return;

        for (File f : fileList) {
            String[] fNameArr = f.getName().split("\\.");
            if (fNameArr.length < 2) continue;
            String fileRoute = f.getAbsolutePath().replace(STATIC_ROOT_DIR, "").replace("." + fNameArr[1], "");
            server.uri(fileRoute + ".{format}", new StaticController())
                    .method(HttpMethod.GET)
                    .name("sample.static.route");
        }

    }


    public static ByteBuffer readContent(String path) throws IOException {

        return readContent(Paths.get(STATIC_ROOT_DIR + path));

    }

    public static ByteBuffer readContent(Path path) throws IOException {

        if (new File(path.toString()).exists()) {
            byte[] fileContent = Files.readAllBytes(path);
            return ByteBuffer.wrap(fileContent);
        }

        return null;
    }

    public static List<File> listf(String directoryName) {

        File directory = new File(directoryName);
        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                //
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }

        return resultList;
    }

    public static boolean fileExists(String path) {
        return (new File(STATIC_ROOT_DIR + path)).exists();
    }

}
