package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.entity.ListProvider;
import net.squirrel.postar.client.entity.Request;
import net.squirrel.postar.client.entity.Response;
import net.squirrel.postar.client.exception.AppException;

/*
Receiver data facade
 */
public class DataManager {
    /*
    @return In the case of internal exclusion gives null
     */
    public static ListProvider receiveProviders() {
        ListProvider listProvider = null;
        DataReceiver providersReceiver = new ProvidersReceiver();
        try {
            listProvider = (ListProvider) providersReceiver.receiveData(null);
        } catch (AppException e) {
            e.printStackTrace();//TODO: Nid Log
        }
        return listProvider;
    }

    /*
    @return In the case of internal exclusion gives null
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
