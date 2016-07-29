package net.squirrel.poshtar.client.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.squirrel.poshtar.client.entity.SavedTrack;

import java.util.ArrayList;
import java.util.List;

public class SQLitePoshtarHandler extends SQLiteOpenHelper implements SavedTrackDAO {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "poshtar";

    private static final String TRACKS_TABLE = "tracks";

    private static final String KEY_T_ID = "_id";
    private static final String KEY_T_ID_PROV = "id_prov";
    private static final String KEY_T_TRACK_NUMBER = "track_number";
    private static final String KEY_T_TRACK_RESULT = "track_result";
    private static final String KEY_T_DESCRIPTION = "description";

    public SQLitePoshtarHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TRACKS_TABLE = "create table" + TRACKS_TABLE +
                "(" + KEY_T_ID + "primary key autoincrement integer," + KEY_T_ID_PROV + "integer," +
                KEY_T_TRACK_NUMBER + "text," + KEY_T_DESCRIPTION + "text)";
        db.execSQL(SQL_CREATE_TRACKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOP
    }


    @Override
    public void addTrack(SavedTrack track) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = setSTrackCV(track);
        database.insert(TRACKS_TABLE, null, contentValues);
        contentValues.clear();
        database.close();
    }

    @Override
    public void removeTrack(Integer id) {
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            database.delete(TRACKS_TABLE, "rowId = " + id, null);
        } finally {
            database.close();
        }
    }

    @Override
    public void updateTrack(Integer id, SavedTrack updatedTrack) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = setSTrackCV(updatedTrack);
        database.update(TRACKS_TABLE, contentValues, "rowId = " + id, null);
        contentValues.clear();
        database.close();
    }

    @Override
    public List<SavedTrack> getTracks() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(TRACKS_TABLE, new String[]{KEY_T_ID_PROV, KEY_T_TRACK_NUMBER,
                KEY_T_TRACK_RESULT, KEY_T_DESCRIPTION}, null, null, null, null, null);
        List<SavedTrack> list = new ArrayList<SavedTrack>();
        for (c.moveToFirst(); c.isLast(); c.moveToNext()) {
            int providerID = c.getInt(c.getColumnIndex(KEY_T_ID));
            String trackNumber = c.getString(c.getColumnIndex(KEY_T_TRACK_NUMBER));
            String trackResult = c.getString(c.getColumnIndex(KEY_T_TRACK_RESULT));
            String description = c.getString(c.getColumnIndex(KEY_T_DESCRIPTION));
            SavedTrack track = new SavedTrack(providerID, trackNumber, trackResult, description);
            list.add(track);
        }
        c.close();
        database.close();
        return list;
    }

    private ContentValues setSTrackCV(SavedTrack track) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_T_ID_PROV, track.getProviderID());
        contentValues.put(KEY_T_TRACK_NUMBER, track.getTrackNumber());
        contentValues.put(KEY_T_TRACK_RESULT, track.getTrackResult());
        contentValues.put(KEY_T_DESCRIPTION, track.getDescription());
        return contentValues;
    }
}
