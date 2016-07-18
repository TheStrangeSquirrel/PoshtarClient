package net.squirrel.poshtar.client.http_client;

import net.squirrel.poshtar.client.exception.AppException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class HttpClient {
    public static String post(String url, String massage) throws AppException {
        String response = null;
        InputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/soap+xml");
            connection.setRequestProperty("Content-Length", String.valueOf(massage.length()));
            connection.setDoOutput(true);
            connection.connect();
            outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(massage.getBytes(Charset.forName("UTF-8")));
            outputStream.flush();
            byte[] arr = null;
            inputStream = connection.getInputStream();
            inputStream.read(arr);
            response = new String(arr);
        } catch (java.io.IOException e) {
            throw new AppException("IO error HttpClient", e);
        } catch (URISyntaxException e) {
            throw new AppException("An error occurred trying to connect to the URL:" + url, e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                //NOP
            }
        }
        return response;
    }
}
