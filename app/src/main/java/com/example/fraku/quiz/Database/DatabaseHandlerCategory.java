package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fraku.quiz.Object.CategoryObject;


public class DatabaseHandlerCategory extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "categoryMenager";
    //Database table name
    private static final String TABLE_CATEGORY = "category";

    // Contacts Table Columns name
    private static final String KEY_ID = "id_cat";
    private static final String KEY_TYTUL = "tytul";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_LICZBA_PYT = "liczba_pytan";

    public DatabaseHandlerCategory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ID + " TEXT,"
                + KEY_TYTUL + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_LICZBA_PYT + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addCategory(CategoryObject categoryObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, categoryObject.getIdCat());
        values.put(KEY_TYTUL, categoryObject.getTytulCat());
        values.put(KEY_IMAGE_URL, categoryObject.getImageUrl());
        values.put(KEY_LICZBA_PYT, categoryObject.getLiczbaPyt());

        //Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);
        return res;
    }

}
