package net.squirrel.postar.client.receiver;

import net.squirrel.postar.client.entity.ListProvider;
import net.squirrel.postar.client.entity.Request;
import net.squirrel.postar.client.entity.Response;

/*
Receiver data facade
 */
public class DataManager {
    public ListProvider receiveProviders() {
        DataReceiver providersReceiver = new ProvidersReceiver();
        return (ListProvider) providersReceiver.receiveData(null);
    }

    public Response track(Request request) {
        DataReceiver trackReceiver = new TrackReceiver();
        return (Response) trackReceiver.receiveData(request);
    }
}
