/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

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
