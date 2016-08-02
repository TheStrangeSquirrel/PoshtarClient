package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.dto.Provider;

import java.io.IOException;
import java.util.List;

public interface ProvidersDAO {
    List<Provider> loadProviders() throws Exception;

    void saveProviders(String providersXML) throws IOException;

    long getTimeLastUpdateProviders();
}
