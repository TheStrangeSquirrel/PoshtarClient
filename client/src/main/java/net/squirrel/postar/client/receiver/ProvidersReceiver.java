package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.entity.ListProvider;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

public class ProvidersReceiver extends DataReceiver {


    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getProvidersUrl();
    }

    @Override
    protected Object deserialization(String xml) {
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();
        Object result = null;
        try {
            result = serializer.read(ListProvider.class, xml);//TODO: Разобраться куда девать исключение
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
