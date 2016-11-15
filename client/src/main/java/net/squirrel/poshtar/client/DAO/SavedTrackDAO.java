/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

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
