/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.client.utils.IOUtils;
import net.squirrel.poshtar.client.utils.XmlTransforming;
import net.squirrel.poshtar.dto.ListProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;
import static net.squirrel.poshtar.client.AppPoshtar.getContext;

public class XMLProviderDAO implements ProvidersDAO {
    private static final String FILENAME = "providers.xml";

    @Override
    public void saveProviders(String providersXML) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = getContext().openFileOutput(FILENAME, MODE_PRIVATE);
            outputStream.write(providersXML.getBytes("UTF-8"));
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    @Override
    public ListProvider loadProviders() throws Exception {
        InputStream inputStream = getContext().openFileInput(FILENAME);
        String providersListXML = IOUtils.toString(inputStream);
        return XmlTransforming.unmarshalling(providersListXML, ListProvider.class);
    }


    @Override
    public long getTimeLastUpdateProvidersFile() {
        File file = getContext().getFileStreamPath(FILENAME);
        return file.lastModified();
    }

    @Override
    public void setTimeLastUpdateProvidersFile(long time) {
        File file = getContext().getFileStreamPath(FILENAME);
        file.setLastModified(time);
    }
}
