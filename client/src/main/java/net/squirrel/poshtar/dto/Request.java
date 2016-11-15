/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.dto;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Request {
    private int providerId;
    private String trackNumber;
    private String language;

    public Request(Integer providerId, String trackNumber, String language) {
        this.trackNumber = trackNumber;
        this.providerId = providerId;
        this.language = language;
    }

    @Attribute
    public int getProviderId() {
        return providerId;
    }

    @Attribute
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @Attribute
    public String getTrackNumber() {
        return trackNumber;
    }

    @Attribute
    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    @Attribute
    public String getLanguage() {
        return language;
    }

    @Attribute
    public void setLanguage(String language) {
        this.language = language;
    }

}
