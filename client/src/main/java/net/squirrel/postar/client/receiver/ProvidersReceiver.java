package net.squirrel.postar.client.receiver;

public class ProvidersReceiver extends DataReceiver {
    private static String URL_SUFFIX = "/providers";

    @Override
    protected void setUrlSuffix() {
        urlSuffix = URL_SUFFIX;
    }

    @Override
    protected Object deserialization(String xml) {
        return null;
    }
}
