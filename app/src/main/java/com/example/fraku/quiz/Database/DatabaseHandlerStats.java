package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fraku.quiz.Object.StatsObject;

public class DatabaseHandlerStats extends SQLiteOpenHelper {

    // All Static variables
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "StatsManager";
    //Database table name
    private static final String TABLE_STATS = "stats";

    // Contacts Table Columns name
    private static final String KEY_ID = "id_Cat";
    private static final String KEY_PROGRESS = "progress";
    private static final String KEY_GOOD_ANSWER = "good_answer";
    private static final String KEY_BAD_ANSWER = "bad_answer";
    private static final String KEY_PERCENT = "percent";

    public DatabaseHandlerStats(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATS_TABLE = "CREATE TABLE " + TABLE_STATS + "("
                + KEY_ID + " TEXT,"
                + KEY_PROGRESS + " INTEGER,"
                + KEY_GOOD_ANSWER + " INTEGER,"
                + KEY_BAD_ANSWER + " INTEGER,"
                + KEY_PERCENT + " INTEGER"+ ")";
        db.execSQL(CREATE_STATS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addStats(StatsObject statsObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, statsObject.getCatId());
        values.put(KEY_PROGRESS, statsObject.getProgress());
        values.put(KEY_GOOD_ANSWER, statsObject.getGoodAnswer());
        values.put(KEY_BAD_ANSWER, statsObject.getBadAnswer());
        values.put(KEY_PERCENT, statsObject.getPercent());


        //Inserting Row
        db.insert(TABLE_STATS, null, values);
        db.close();
    }

    public Cursor getAllStatsData(String QueId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllStats = db.rawQuery("SELECT * FROM " + TABLE_STATS + " WHERE " + KEY_ID + " IS " + QueId, null);
        return AllStats;
    }

    public Cursor getResultStats(String QueId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor ResultStats = db.rawQuery(" SELECT " + KEY_PERCENT + ", " + KEY_PROGRESS + ", " + KEY_GOOD_ANSWER + " FROM "
                + TABLE_STATS + " WHERE " + KEY_ID + " IS " + QueId, null);

        return ResultStats;
    }

    public void removeSaveQuiz(String QueId) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + TABLE_STATS + " WHERE " + KEY_ID + " IS '" + QueId + "'");

        //Close the database
        database.close();
    }
}
