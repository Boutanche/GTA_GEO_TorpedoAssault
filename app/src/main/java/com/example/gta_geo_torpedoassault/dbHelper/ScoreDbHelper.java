package com.example.gta_geo_torpedoassault.dbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gta_geo_torpedoassault.contracts.ScoreContract;
import com.example.gta_geo_torpedoassault.models.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ScoreReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreContract.ScoreEntry.TABLE_NAME + " (" +
                    ScoreContract.ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreContract.ScoreEntry.COLUMN_NAME_SCORE + " INT," +
                    ScoreContract.ScoreEntry.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME;

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public List<Score> getAllScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Score> scores = new ArrayList<>();
        Cursor cursor = null;
        try {
            String[] projection = {
                    ScoreContract.ScoreEntry._ID,
                    ScoreContract.ScoreEntry.COLUMN_NAME_SCORE,
                    ScoreContract.ScoreEntry.COLUMN_NAME_DATE
            };

            String sortOrder =
                    ScoreContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC";

            cursor = db.query(
                    ScoreContract.ScoreEntry.TABLE_NAME,   // The table to query
                    projection,                             // The columns to return
                    null,                                  // The columns for the WHERE clause
                    null,                                  // The values for the WHERE clause
                    null,                                  // Don't group the rows
                    null,                                  // Don't filter by row groups
                    sortOrder                              // The sort order
            );

            while (cursor.moveToNext()) {
                int score = cursor.getInt(
                        cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE));
                long date = cursor.getLong(
                        cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_NAME_DATE));

                scores.add(new Score(score, date));
            }

        } catch (Exception e) {
            Log.e("ScoreDbHelper : Error", "Failed to get all scores", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return scores;
    }

}