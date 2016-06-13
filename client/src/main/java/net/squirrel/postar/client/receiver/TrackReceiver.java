package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.entity.Response;
import net.squirrel.postar.client.exception.AppException;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

public class TrackReceiver extends DataReceiver {
    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getProvidersUrl();
    }

    @Override
    protected Object deserialization(String xml) {
        Reader reader = new StringReader(xml);
        Serializer serializer = new Persister();
        Object result = null;
        try {
            result = serializer.read(Response.class, xml);
        } catch (Exception e) {
            throw new AppException("Error during deserialization response", e);
        }
        return result;
    }
}