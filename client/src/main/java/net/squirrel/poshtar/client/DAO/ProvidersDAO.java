package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.dto.Provider;

import java.util.List;

public interface ProvidersDAO {
    public List<Provider> loadProviders();

    public void saveProviders(List<Provider> providers);

    public int getTimeLastUpdateProviders();
}
