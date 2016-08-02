package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.client.ConfigManager;
import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.http_client.HttpClient;
import net.squirrel.poshtar.client.utils.XmlTransforming;
import net.squirrel.poshtar.dto.ListProvider;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Response;

import java.util.List;

public class DataReceiver {
    private ConfigManager config;

    public DataReceiver() {
        config = new ConfigManager();
    }

    public String receiveProvidersXML() throws AppException {
        String url = config.getBaseUrl() + config.getProvidersUrl();
        return HttpClient.post(url, null);
    }

    public List<Provider> getProviderListFromXML(String xml) throws Exception {
        ListProvider unmarshalling = XmlTransforming.unmarshalling(xml, ListProvider.class);
        return unmarshalling.getProviders();
    }

    public Response track(Request request) throws Exception {
        String url = config.getBaseUrl() + config.getTrackingUrl();
        String marshalling = XmlTransforming.marshalling(request);
        String response = HttpClient.post(url, marshalling);
        return XmlTransforming.unmarshalling(response, Response.class);
    }
}
