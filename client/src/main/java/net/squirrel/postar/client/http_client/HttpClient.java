package net.squirrel.postar.client.http_client;

import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class HttpClient {
    public static String post(String url, String massage) {
        String response = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(massage.length()));
            connection.setDoOutput(true);
            connection.setConnectTimeout(1000000);
            connection.setReadTimeout(1000000);
            connection.connect();
            BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(massage.getBytes(Charset.forName("UTF-8")));
            outputStream.flush();

            System.out.println(connection.getResponseCode());
            byte[] arr = null;
            connection.getInputStream().read(arr);
            response = new String(arr);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return response;
    }
}
