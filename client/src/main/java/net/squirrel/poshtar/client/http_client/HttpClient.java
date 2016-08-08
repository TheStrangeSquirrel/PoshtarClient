package net.squirrel.poshtar.client.http_client;

import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.IOUtils;
import net.squirrel.poshtar.client.utils.LogUtil;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpClient {
    public static String post(String url, String massage) throws AppException {
        String response = null;
        InputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded,text/xml,text/html; charset=UTF-8");
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
            LogUtil.w("IO error HttpClient", e);

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
