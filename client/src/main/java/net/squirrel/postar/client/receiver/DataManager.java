package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.receiver.dto.ListProvider;
import net.squirrel.postar.client.receiver.dto.Request;
import net.squirrel.postar.client.entity.Provider;
import net.squirrel.postar.client.entity.Response;
import net.squirrel.postar.client.exception.AppException;

import java.util.List;

/*
 *Receiver data facade
 */
public class DataManager {
    /*
     *@return In the case of internal exclusion gives null
     */
    public static List<Provider> receiveProviders() {
        List<Provider> listProvider = null;
        DataReceiver providersReceiver = new ProvidersReceiver();
        try {
            listProvider = ((ListProvider) providersReceiver.receiveData(null)).getProviders();
        } catch (AppException e) {
            e.printStackTrace();//TODO: Nid Log
        }
        return listProvider;
    }

    /*
    *@return In the case of internal exclusion gives null
    */
    public static Response track(Request request) {
        Response response = null;
        DataReceiver trackReceiver = new TrackReceiver();
        try {
            response = (Response) trackReceiver.receiveData(request);
        } catch (AppException e) {
            e.printStackTrace();//TODO: Nid Log
        }
        return response;
    }
}
