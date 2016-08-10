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

    public String getDescription() {
        return description;
    }

}
