package net.squirrel.postar.client.receiver;

public class ProvidersReceiver extends DataReceiver {


    @Override
    protected void setUrlSuffix() {
        urlSuffix = config.getProvidersUrl();
    }

    @Override
    protected Object deserialization(String xml) {
        throw new UnsupportedOperationException();//TODO
    }
}
