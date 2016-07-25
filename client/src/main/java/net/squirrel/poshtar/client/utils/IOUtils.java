package net.squirrel.poshtar.client.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 10;

    public static String toString(InputStream inputStream, String charsetName, int bufferSizeInByte) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[bufferSizeInByte];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(charsetName);
    }

    public static String toString(InputStream inputStream, int bufferSizeInByte) throws IOException {
        return toString(inputStream, "UTF-8", bufferSizeInByte);
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, "UTF-8", DEFAULT_BUFFER_SIZE);
    }
}
