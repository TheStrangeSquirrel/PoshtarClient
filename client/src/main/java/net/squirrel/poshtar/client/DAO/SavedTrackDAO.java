package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.client.entity.SavedTrack;

import java.util.List;

public interface SavedTrackDAO {
    int isExistThere(Integer providerId, String track_number);
    void addTrack(SavedTrack track);

    void updateTrack(Integer id, SavedTrack updatedTrack);
    void removeTrack(Integer id);

    void cleanTrack();


    List<SavedTrack> getTracks();
}
