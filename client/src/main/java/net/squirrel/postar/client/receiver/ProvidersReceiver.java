package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.receiver.dto.ListProvider;
import net.squirrel.postar.client.exception.AppException;
import net.squirrel.postar.client.utils.LogUtil;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

public class ProvidersReceiver extends DataReceiver {


    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getProvidersUrl();
    }

    @Override
    protected Object deserialization(String xml) throws AppException {
        Reader reader = new StringReader(xml);
        Serializer serializer = new Persister();
        Object result = null;
        try {
            result = serializer.read(ListProvider.class, xml);
        } catch (Exception e) {
            LogUtil.d("Error during deserialization providers");
            throw new AppException("Error during deserialization providers", e);
        }
        return result;
    }
}
