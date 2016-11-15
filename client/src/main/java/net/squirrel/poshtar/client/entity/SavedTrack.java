/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.entity;

import java.io.Serializable;

public class SavedTrack implements Serializable {
    private int id;
    private int providerID;
    private String providerName;
    private String trackNumber;


    private String trackResult;
    private String description;

    public SavedTrack(int id, int providerID, String providerName, String trackNumber, String trackResult, String description) {
        this.id = id;
        this.providerID = providerID;
        this.providerName = providerName;
        this.trackNumber = trackNumber;
        this.trackResult = trackResult;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProviderID() {
        return providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public String getTrackResult() {
        return trackResult;
    }

    public void setTrackResult(String trackResult) {
        this.trackResult = trackResult;
    }

    public String getDescription() {
        return description;
    }

}
