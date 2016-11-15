package net.squirrel.poshtar.client.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.squirrel.poshtar.client.entity.SavedTrack;
import net.squirrel.poshtar.client.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SQLitePoshtarHelper extends SQLiteOpenHelper implements SavedTrackDAO {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "poshtar";

    private static final String TRACKS_TABLE = "tracks";

    private static final String KEY_T_ID = "_id";
    private static final String KEY_T_ID_PROV = "id_prov";
    private static final String KEY_T_NAME_PROV = "name_prov";
    private static final String KEY_T_TRACK_NUMBER = "track_number";
    private static final String KEY_T_TRACK_RESULT = "track_result";
    private static final String KEY_T_DESCRIPTION = "description";

    public SQLitePoshtarHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TRACKS_TABLE = "create table " + TRACKS_TABLE +
                "(" + KEY_T_ID + " integer primary key autoincrement, " +
                KEY_T_ID_PROV + " integer, " + KEY_T_NAME_PROV + " text, " +
                KEY_T_TRACK_NUMBER + " text, " + KEY_T_TRACK_RESULT + " text, " + KEY_T_DESCRIPTION + " text )";
        db.execSQL(SQL_CREATE_TRACKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE " + TRACKS_TABLE);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE " + TRACKS_TABLE);
            onCreate(db);
        }
    }


    @Override
    public int isExistThere(Integer providerId, String track_number) {
        SQLiteDatabase database = this.getReadableDatabase();
        String where = KEY_T_ID_PROV + " = " + providerId + " and " + KEY_T_TRACK_NUMBER + " = '" + track_number + "'";
        Cursor c = database.query(TRACKS_TABLE, new String[]{KEY_T_ID}, where, null, null, null, null);
        if (c.moveToFirst()) {
            return c.getInt(c.getColumnIndex(KEY_T_ID));
        } else {
            return -1;
        }
    }

    @Override
    public void addTrack(SavedTrack track) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = setSTrackCV(track);
        long insert = database.insert(TRACKS_TABLE, null, contentValues);
        LogUtil.i("Row ID of the newly inserted  - " + insert);
        contentValues.clear();
        database.close();
    }

    @Override
    public void removeTrack(Integer id) {
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            database.delete(TRACKS_TABLE, KEY_T_ID + " = " + id, null);
        } finally {
            database.close();
        }
    }

    @Override
    public void cleanTrack() {
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            database.delete(TRACKS_TABLE, null, null);
        } finally {
            try {
                database.close();
            } catch (RuntimeException e) {
                //nop
            }

        }
    }

    @Override
    public void updateTrack(Integer id, SavedTrack updatedTrack) {
        LogUtil.d("updateTrack id:" + id + " TrackResult:" + updatedTrack.getTrackResult());
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = setSTrackCV(updatedTrack);
        database.update(TRACKS_TABLE, contentValues, KEY_T_ID + " = " + id, null);
        contentValues.clear();
        database.close();
    }

    @Override
    public List<SavedTrack> getTracks() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(TRACKS_TABLE, new String[]{KEY_T_ID, KEY_T_ID_PROV, KEY_T_NAME_PROV, KEY_T_TRACK_NUMBER,
                KEY_T_TRACK_RESULT, KEY_T_DESCRIPTION}, null, null, null, null, null);

        List<SavedTrack> list = new ArrayList<>();
        if (c.moveToFirst()) {
            int iId = c.getColumnIndex(KEY_T_ID);
            int iProviderID = c.getColumnIndex(KEY_T_ID_PROV);
            int iProviderName = c.getColumnIndex(KEY_T_NAME_PROV);
            int iTrackNumber = c.getColumnIndex(KEY_T_TRACK_NUMBER);
            int iTrackResult = c.getColumnIndex(KEY_T_TRACK_RESULT);
            int iDescription = c.getColumnIndex(KEY_T_DESCRIPTION);
            do {
                int id = c.getInt(iId);
                int providerID = c.getInt(iProviderID);
                String trackNumber = c.getString(iTrackNumber);
                String providerName = c.getString(iProviderName);
                String trackResult = c.getString(iTrackResult);
                String description = c.getString(iDescription);
                SavedTrack track = new SavedTrack(id, providerID, providerName, trackNumber, trackResult, description);
                list.add(track);
            } while (c.moveToNext());
        }
        c.close();
        database.close();
        return list;
    }

    private ContentValues setSTrackCV(SavedTrack track) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_T_ID_PROV, track.getProviderID());
        contentValues.put(KEY_T_NAME_PROV, track.getProviderName());
        contentValues.put(KEY_T_TRACK_NUMBER, track.getTrackNumber());
        contentValues.put(KEY_T_TRACK_RESULT, track.getTrackResult());
        contentValues.put(KEY_T_DESCRIPTION, track.getDescription());
        return contentValues;
    }
}
