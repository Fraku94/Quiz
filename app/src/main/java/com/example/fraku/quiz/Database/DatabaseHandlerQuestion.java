package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fraku.quiz.Object.QuestionObject;


/**
 * Created by Fraku on 25.01.2018.
 */

public class DatabaseHandlerQuestion extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "OdpManager";
    //Database table name
    private static final String TABLE_QUSESTION = "pytania";

    // Contacts Table Columns name
    private static final String KEY_ID = "id_Cat";
    private static final String KEY_PYTANIE = "pytanie";
    private static final String KEY_ODPOWIEDZ = "odpowiedz";
    private static final String KEY_WYNIK_ODP = "wynik_odp";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_NUMER_PYT = "numer_pyt";
    private static final String KEY_NUMER_ODP = "numer_odp";



    public DatabaseHandlerQuestion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_QUSESTION + "("
                + KEY_ID + " TEXT,"
                + KEY_PYTANIE + " TEXT,"
                + KEY_ODPOWIEDZ + " TEXT,"
                + KEY_WYNIK_ODP + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_NUMER_PYT + " TEXT,"
                + KEY_NUMER_ODP + " TEXT"+ ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUSESTION);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addQuestion(QuestionObject questionObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, questionObject.getCatId());
        values.put(KEY_PYTANIE, questionObject.getPytanie());
        values.put(KEY_ODPOWIEDZ, questionObject.getOdpowiedz());
        values.put(KEY_WYNIK_ODP, questionObject.getWynikOpd());
        values.put(KEY_IMAGE_URL, questionObject.getImageUrl());
        values.put(KEY_NUMER_PYT, questionObject.getNumerPyt());
        values.put(KEY_NUMER_ODP, questionObject.getNumerOdp());


        //Inserting Row
        db.insert(TABLE_QUSESTION, null, values);
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllData = db.rawQuery("SELECT * FROM " + TABLE_QUSESTION, null);
        return AllData;
    }

    public Cursor getQuestion(String CatId, String NumerPyt) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor Question = db.rawQuery(" SELECT " + KEY_PYTANIE + ", " + KEY_ODPOWIEDZ + ", " + KEY_WYNIK_ODP + ", " + KEY_NUMER_PYT + ", " + KEY_NUMER_ODP + " FROM "
                + TABLE_QUSESTION + " WHERE " + KEY_ID + " IS " + CatId + " AND " + KEY_NUMER_PYT + " IS " + NumerPyt, null);

        return Question;
    }
}
