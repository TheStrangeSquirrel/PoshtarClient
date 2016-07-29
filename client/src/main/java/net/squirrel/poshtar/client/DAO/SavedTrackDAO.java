package net.squirrel.poshtar.client.DAO;

import net.squirrel.poshtar.client.entity.SavedTrack;

import java.util.List;

public interface SavedTrackDAO {
    public void addTrack(SavedTrack track);

    public void removeTrack(Integer id);

    public void updateTrack(Integer id, SavedTrack updatedTrack);

    public List<SavedTrack> getTracks();
}
