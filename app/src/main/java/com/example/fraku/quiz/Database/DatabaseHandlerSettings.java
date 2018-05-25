package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerSettings extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "SettingsManager";
    //Database table name
    private static final String TABLE_SETTINGS= "settings";

    // Contacts Table Columns name
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";

    public DatabaseHandlerSettings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_DATE + " INTEGER"+ ")";
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addSettings(long Date) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        //Create table again
        onCreate(db);
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, Date);

        //Inserting Row
        db.insert(TABLE_SETTINGS, null, values);
        db.close();
    }

    public Cursor getAllSettingsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllSettings = db.rawQuery("SELECT * FROM " + TABLE_SETTINGS, null);

        return AllSettings;
    }

    public Cursor getDateSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor DateSettings = db.rawQuery(" SELECT " + KEY_DATE + " FROM "
                + TABLE_SETTINGS , null);

        return DateSettings;
    }
}