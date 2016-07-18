package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.client.ConfigManager;
import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.http_client.HttpClient;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Request;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;

/**
 *The class responsible for conversion requests/
 */
public abstract class DataReceiver {
    protected ConfigManager config;
    protected String baseUrl, url, urlSuffix;

    DataReceiver() {
        config = new ConfigManager();
        baseUrl = config.getBaseUrl();
    }

    /**
     *@param request If you want to get a list parameter must be null.
     *@return Deserialization response
     */
    Object receiveData(Request request) throws AppException {
        setUrlSuffix();
        url = baseUrl + urlSuffix;

        String requestString = serialization(request);
        String responseString = HttpClient.post(this.url, requestString);
        return deserialization(responseString);
    }

    protected String serialization(Object objects) throws AppException {
        String result;
        Writer writer = new StringWriter();
        org.simpleframework.xml.Serializer serializer = new Persister();
        try {
            serializer.write(objects, writer);
        } catch (Exception e) {
            LogUtil.d("Error during serialization");
            throw new AppException("Error during serialization", e);
        }
        result = writer.toString();
        return result;
    }

    //Override this method for set  valid url suffix
    protected abstract void setUrlSuffix();

    protected abstract <T> T deserialization(String xml) throws AppException;

}
