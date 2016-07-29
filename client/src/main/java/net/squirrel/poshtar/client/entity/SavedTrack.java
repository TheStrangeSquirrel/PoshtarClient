package net.squirrel.poshtar.client.entity;

public class SavedTrack {
    private int providerID;
    private String trackNumber;
    private String trackResult;
    private String description;

    public SavedTrack() {
    }

    public SavedTrack(int providerID, String trackNumber, String trackResult, String description) {
        this.providerID = providerID;
        this.trackNumber = trackNumber;
        this.trackResult = trackResult;
        this.description = description;
    }

    public int getProviderID() {
        return providerID;
    }

    public void setProviderID(int providerID) {
        this.providerID = providerID;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
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

    public void setDescription(String description) {
        this.description = description;
    }
}
