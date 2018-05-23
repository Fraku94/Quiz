package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fraku.quiz.Object.QuestionObject;


public class DatabaseHandlerQuestion extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "QuestionMenager";
    //Database table name
    private static final String TABLE_QUESTION = "Question";

    // Contacts Table Columns name
    private static final String KEY_ID = "id_que";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_QUESTION_NUM = "question_num";

    public DatabaseHandlerQuestion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_QUESTION + "("
                + KEY_ID + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_QUESTION_NUM + " TEXT" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addQuestion(QuestionObject questionObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, questionObject.getIdQue());
        values.put(KEY_TITLE, questionObject.getTitleQue());
        values.put(KEY_IMAGE_URL, questionObject.getImageUrl());
        values.put(KEY_QUESTION_NUM, questionObject.getQuestionNum());

        //Inserting Row
        db.insert(TABLE_QUESTION, null, values);
        db.close();
    }

    public Cursor getAllQuestionData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllQuestion = db.rawQuery("SELECT * FROM " + TABLE_QUESTION, null);
        return AllQuestion;
    }
}
