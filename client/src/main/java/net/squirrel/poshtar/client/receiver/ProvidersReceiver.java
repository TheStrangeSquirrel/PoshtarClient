package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.dto.ListProvider;
import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.LogUtil;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.*;
import java.nio.charset.Charset;

public class ProvidersReceiver extends DataReceiver {


    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getProvidersUrl();
    }

    @Override
    protected ListProvider deserialization(String xml) throws AppException {
        Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
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
