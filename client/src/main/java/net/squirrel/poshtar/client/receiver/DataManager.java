package net.squirrel.poshtar.client.receiver;

import net.squirrel.poshtar.client.exception.AppException;
import net.squirrel.poshtar.client.utils.LogUtil;
import net.squirrel.poshtar.dto.ListProvider;
import net.squirrel.poshtar.dto.Request;
import net.squirrel.poshtar.dto.Provider;
import net.squirrel.poshtar.dto.Response;

import java.util.List;

/*
 *Receiver data facade
 */
public class DataManager {
    /**
     *@return In the case of internal exclusion gives null
     */
    public static List<Provider> receiveProviders() {
        List<Provider> listProvider = null;
        DataReceiver providersReceiver = new ProvidersReceiver();
        try {
            listProvider = ((ListProvider) providersReceiver.receiveData(null)).getProviders();
        } catch (AppException e) {
            LogUtil.w("Providers list is not received", e);
        }
        return listProvider;
    }

    /**
    *@return In the case of internal exclusion gives null
    */
    public static Response track(Request request) {
        Response response = null;
        DataReceiver trackReceiver = new TrackReceiver();
        try {
            response = (Response) trackReceiver.receiveData(request);
        } catch (AppException e) {
            LogUtil.w("Failed track", e);
        }
        return response;
    }
}
