package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.dto.ListProvider;

import java.io.IOException;

public interface ProvidersDAO {
    ListProvider loadProviders() throws Exception;

    void saveProviders(String providersXML) throws IOException;

    /**
     * @return Returns 0 if the file does not exist
     */
    long getTimeLastUpdateProvidersFile();

    void setTimeLastUpdateProvidersFile(long time);
}
