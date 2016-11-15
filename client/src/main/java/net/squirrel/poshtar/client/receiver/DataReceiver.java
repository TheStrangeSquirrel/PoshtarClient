/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.client.UrlConfigManager;
import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.http_client.HttpClient;
import net.squirrel.poshtar.client.utils.XmlTransforming;
import net.squirrel.poshtar.dto.*;

import java.io.IOException;
import java.util.List;

public class DataReceiver {
    private UrlConfigManager config;

    public DataReceiver() {
        config = new UrlConfigManager();
    }

    public ServerSettings receiveServerSettings() throws Exception {
        String response = HttpClient.get(config.getBaseUrl() + UrlConfigManager.SERVER_SETTINGS_URL_SUFFIX);
        ServerSettings settings = XmlTransforming.unmarshalling(response, ServerSettings.class);
        config.updateUrl(settings.getFutureUrl());
        return settings;
    }

    public String receiveProvidersXML() throws AppException, IOException {
        String url = config.getBaseUrl() + UrlConfigManager.PROVIDERS_URL_SUFFIX;
        return HttpClient.post(url, null);
    }

    public List<Provider> getProviderListFromXML(String xml) throws Exception {
        ListProvider unmarshalling = XmlTransforming.unmarshalling(xml, ListProvider.class);
        return unmarshalling.getProviders();
    }

    public Response track(Request request) throws Exception {
        String url = config.getBaseUrl() + UrlConfigManager.TRACKING_URL_SUFFIX;
        String marshalling = XmlTransforming.marshalling(request);
        String response = HttpClient.post(url, marshalling);
        return XmlTransforming.unmarshalling(response, Response.class);
    }
}
