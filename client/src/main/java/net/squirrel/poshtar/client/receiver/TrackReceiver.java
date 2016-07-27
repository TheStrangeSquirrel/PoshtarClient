package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.Response;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.Reader;
import java.io.StringReader;

public class TrackReceiver extends DataReceiver {
    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getTrackingUrl();
    }

    @Override
    protected Object deserialization(String xml) throws AppException {
        Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
        Object result = null;
        try {
            result = serializer.read(Response.class, xml);
        } catch (Exception e) {
            LogUtil.d("Error during deserialization response");
            throw new AppException("Error during deserialization response", e);
        }
        return result;
    }
}
