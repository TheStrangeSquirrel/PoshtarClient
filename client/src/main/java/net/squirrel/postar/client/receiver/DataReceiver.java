package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.entity.Request;
import net.squirrel.postar.client.http_client.HttpClient;
import org.simpleframework.xml.core.Persister;

import java.io.StringWriter;
import java.io.Writer;

public abstract class DataReceiver {
    protected static final String BASE_URL = "";
    protected String url, urlSuffix;


    public DataReceiver() {

    }

    public Object receiveData(Request request) {
        setUrlSuffix();
        url = BASE_URL + urlSuffix;

        String requestString = serialization(request);
        String responseString = HttpClient.post(this.url, requestString);
        return deserialization(requestString);
    }

    protected String serialization(Object objects) {
        String result;
        Writer writer = new StringWriter();
        org.simpleframework.xml.Serializer serializer = new Persister();
        try {
            serializer.write(objects, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = writer.toString();
        return result;
    }

    //Override this method for set  valid url suffix
    protected abstract void setUrlSuffix();

    protected abstract Object deserialization(String xml);

}
