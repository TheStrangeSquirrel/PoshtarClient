package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.ConfigManager;
import net.squirrel.postar.client.entity.Request;
import net.squirrel.postar.client.exception.AppException;
import net.squirrel.postar.client.http_client.HttpClient;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;

/*
The class responsible for conversion requests/
 */
public abstract class DataReceiver {
    protected ConfigManager config;
    protected String baseUrl, url, urlSuffix;

    public DataReceiver() {
        config = new ConfigManager();
        baseUrl = config.getBaseUrl();
    }

    /*
    @param request If you want to get a list parameter must be null.
    @result Deserialization response
     */
    public Object receiveData(Request request) {
        setUrlSuffix();
        url = baseUrl + urlSuffix;

        String requestString = serialization(request);
        String responseString = HttpClient.post(this.url, requestString);
        return deserialization(responseString);
    }

    protected String serialization(Object objects) {
        String result;
        Writer writer = new StringWriter();
        org.simpleframework.xml.Serializer serializer = new Persister();
        try {
            serializer.write(objects, writer);
        } catch (Exception e) {
            throw new AppException("Error during serialization", e);
        }
        result = writer.toString();
        return result;
    }

    //Override this method for set  valid url suffix
    protected abstract void setUrlSuffix();
    protected abstract Object deserialization(String xml);

}
