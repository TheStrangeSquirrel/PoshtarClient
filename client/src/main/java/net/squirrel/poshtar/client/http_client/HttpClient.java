package net.squirrel.poshtar.client.http_client;

import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.IOUtils;

import java.io.*;
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
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded,text/xml,text/html");
            connection.setDoOutput(true);
            connection.connect();
            outputStream = new BufferedOutputStream(connection.getOutputStream());
            if (massage != null) {
                outputStream.write(massage.getBytes(Charset.forName("UTF-8")));
            }
            outputStream.flush();
            inputStream = connection.getInputStream();
            response = IOUtils.toString(inputStream);

        } catch (IOException e) {
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
        return response.toString();
    }
}
