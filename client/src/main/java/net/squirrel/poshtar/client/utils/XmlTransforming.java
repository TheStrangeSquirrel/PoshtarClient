/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.StringWriter;
import java.io.Writer;

public class XmlTransforming {
    public static String marshalling(Object entity) throws Exception {
        Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
        Writer writer = new StringWriter();
        serializer.write(entity, writer);
        writer.flush();
        return writer.toString();
    }

    public static <T> T unmarshalling(String xml, Class<T> clazz) throws Exception {
        Persister serializer = new Persister(new Format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"));
        T result = serializer.read(clazz, xml, false);
        return result;
    }
}
