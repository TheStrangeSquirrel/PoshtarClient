package net.squirrel.poshtar.client.receiver;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

public class Serializer<T> {

    public T deserialization(String xml, Class<? extends T> type) {
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();
        T result = null;
        try {
            result = serializer.read(type, reader, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
