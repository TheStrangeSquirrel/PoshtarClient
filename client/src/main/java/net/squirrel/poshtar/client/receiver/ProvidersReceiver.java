package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.dto.ListProvider;
import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.LogUtil;
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
    protected ListProvider deserialization(String xml) throws AppException {
        Reader reader = new StringReader(xml);
        Serializer serializer = new Persister();
        ListProvider result = null;
        try {
            result = serializer.read(ListProvider.class, xml);
        } catch (Exception e) {
            LogUtil.d("Error during deserialization providers");
            throw new AppException("Error during deserialization providers", e);
        }
        return result;
    }
}
