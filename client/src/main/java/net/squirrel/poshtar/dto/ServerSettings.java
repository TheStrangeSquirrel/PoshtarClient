package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Attribute;

public class ServerSettings {
    private String currentVersion;
    private String futureUrl;

    public ServerSettings() {
    }

    public ServerSettings(String currentVersion, String futureUrl) {
        this.currentVersion = currentVersion;
        this.futureUrl = futureUrl;
    }

    @Attribute
    public String getCurrentVersion() {
        return currentVersion;
    }

    @Attribute
    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    @Attribute
    public String getFutureUrl() {
        return futureUrl;
    }

    @Attribute
    public void setFutureUrl(String futureUrl) {
        this.futureUrl = futureUrl;
    }

}
