/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

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
