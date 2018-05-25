package com.example.fraku.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fraku.quiz.Object.AnswerObject;

public class DatabaseHandlerAnswer extends SQLiteOpenHelper {

    // All Static variables

    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "OdpManager";
    //Database table name
    private static final String TABLE_ANSWER = "answer";

    // Contacts Table Columns name
    private static final String KEY_ID = "id_que";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_QUESTION_RESULT = "question_result";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_QUESTION_NUM = "question_num";
    private static final String KEY_ANSWER_NUM = "answer_num";

    public DatabaseHandlerAnswer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE " + TABLE_ANSWER + "("
                + KEY_ID + " TEXT,"
                + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER + " TEXT,"
                + KEY_QUESTION_RESULT + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_QUESTION_NUM + " TEXT,"
                + KEY_ANSWER_NUM + " TEXT"+ ")";
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);

        //Create table again
        onCreate(db);
    }

    //Adding new contact
    public void addAnswer(AnswerObject answerObject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, answerObject.getIdQue());
        values.put(KEY_QUESTION, answerObject.getQuestion());
        values.put(KEY_ANSWER, answerObject.getAnswer());
        values.put(KEY_QUESTION_RESULT, answerObject.getQuestionResult());
        values.put(KEY_IMAGE_URL, answerObject.getImageUrl());
        values.put(KEY_QUESTION_NUM, answerObject.getQuestionNum());
        values.put(KEY_ANSWER_NUM, answerObject.getAnswerNum());

        //Inserting Row
        db.insert(TABLE_ANSWER, null, values);
        db.close();
    }

    public Cursor getAllAnswerData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor AllData = db.rawQuery("SELECT * FROM " + TABLE_ANSWER, null);
        return AllData;
    }

    public Cursor getAnswer(String QueId, int QuestionNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor Answer = db.rawQuery(" SELECT " + KEY_QUESTION + ", " + KEY_ANSWER + ", " + KEY_QUESTION_RESULT + ", " + KEY_QUESTION_NUM + ", " + KEY_ANSWER_NUM + " FROM "
                + TABLE_ANSWER + " WHERE " + KEY_ID + " IS " + QueId + " AND " + KEY_QUESTION_NUM + " IS " + QuestionNum, null);

        return Answer;
    }

    public void onClear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER);
        onCreate(db);
    }
}
