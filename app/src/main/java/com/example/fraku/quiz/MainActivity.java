package com.example.fraku.quiz;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.fraku.quiz.Question.QuestionAdapter;
import com.example.fraku.quiz.Question.SimpleDividerItemDecoration;
import com.example.fraku.quiz.Database.DatabaseHandlerQuestion;
import com.example.fraku.quiz.Database.DatabaseHandlerAnswer;
import com.example.fraku.quiz.Database.DatabaseHandlerSettings;
import com.example.fraku.quiz.Object.AnswerObject;
import com.example.fraku.quiz.Object.QuestionObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private String IdQue, TitleQue, ImageUrlCat, QuestionNum, Question, ImageUrlOdp, CurrentQuestionNum, AnswerNum, Answer, QuestionResult;

    private Long lastCheckedMillis;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mCategoryAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutMenager;

    private DatabaseHandlerQuestion databaseQuestion;
    private DatabaseHandlerAnswer databaseAnswer;
    private DatabaseHandlerSettings databaseSettings;

    private  NetworkInfo netInfo;

    private   Bitmap theBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Przypisanie funkicji odswiezania
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setRefreshing(true);

        databaseQuestion = new DatabaseHandlerQuestion(this);
        databaseAnswer = new DatabaseHandlerAnswer(this);
        databaseSettings = new DatabaseHandlerSettings(this);

        //Ustawienie RecycleView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);




        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        //Ustawienie Adaptera oraz LayoutMenagera. Uzycie Contextu fragmentu
        mCategoryLayoutMenager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mCategoryLayoutMenager);
        mCategoryAdapter = new QuestionAdapter(getDataCat(),getApplicationContext());
        mRecyclerView.setAdapter(mCategoryAdapter);

        //Sprawdzenie dostępu do internetu
        isOnline();

        //Sprawdzenie daty odswieżenia
        getDate();

        //Inicjacja odswierzania
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isOnline();

                if (netInfo != null) {

                    clear();
                    new GetCategory().execute();

                    mCategoryAdapter = new QuestionAdapter(getDataCat(),getApplicationContext());
                    mRecyclerView.setAdapter(mCategoryAdapter);

                }else if (netInfo == null){
                    Toast.makeText(MainActivity.this,"Brak połączenia z interentem",Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


    }

    private void getDate() {

        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        long now = cal.getTimeInMillis();

        Cursor resDate = databaseSettings.getDateSettings();

        if (resDate.getCount() == 0){

            databaseSettings.addSettings(now);

            Log.e("Data2", "now " + now);
            isOnline();

            if (netInfo != null){

                new GetCategory().execute();

            }else{

                Toast.makeText(MainActivity.this,"Brak połączenia z interentem, włącz internet i zresetuj aplikacje",Toast.LENGTH_LONG).show();
            }
        }else if(resDate.moveToPosition(0)) {

            lastCheckedMillis = (resDate.getLong(0));

            long diffMillis = now - lastCheckedMillis;

            if( diffMillis >= (3600000  * 24) ) {
                databaseSettings.addSettings(now);

                isOnline();

                if (netInfo != null){

                    new GetCategory().execute();

                }else{

                    Toast.makeText(MainActivity.this,"Brak połączenia z interentem, włącz internet i zresetuj aplikacje",Toast.LENGTH_LONG).show();
                }

            } else {

                // too early
                Cursor res = databaseQuestion.getAllQuestionData();

                while (res.moveToNext()) {
                    QuestionObject object = new QuestionObject(res.getString(0), res.getString(1), res.getString(2), res.getString(3));

                    //Metoda dodawania do Objektu
                    resoult.add(object);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    //Czyszczenie
    private void clear() {
        int size = this.resoult.size();
        this.resoult.clear();
        mCategoryAdapter.notifyItemRangeChanged(0, size);
    }

    //Przeslanie do Adaptera Rezultatow
    private ArrayList<QuestionObject> resoult = new ArrayList<QuestionObject>();


    private List<QuestionObject> getDataCat() {
       // new GetCategory().execute();
        return resoult;
    }

    private class GetCategory extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            if (netInfo != null) {

                databaseQuestion.Clear();
                databaseAnswer.onClear();

                HttpHandler sh = new HttpHandler();

                //Link do Quizu
                String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";
                String jsonStr = sh.makeServiceCall(url);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // JSON Array Quizu
                        JSONArray Quiz = jsonObj.getJSONArray("items");

                        //Przejscie do JSONObject
                        JSONObject resultObject = (JSONObject) Quiz.get(0);

                        // Pobieranie
                        for (int i = 0; i < Quiz.length(); i++) {
                            JSONObject c = Quiz.getJSONObject(i);

                            //ID Quizu
                            IdQue = c.getString("id");

                            //Link do szczegołów
                            String urlq = "http://quiz.o2.pl/api/v1/quiz/"+ IdQue +"/0";

                            String jsonStrPyt = sh.makeServiceCall(urlq);

                            if (jsonStrPyt != null) {
                                try {
                                    JSONObject jsonObjPyt = new JSONObject(jsonStrPyt);

                                    // JSON Array pytań
                                    JSONArray Que = jsonObjPyt.getJSONArray("questions");

                                    JSONObject resultObjectAns = (JSONObject) Que.get(0);

                                    // Pobieranie danych pytan
                                    for (int q = 0; q < Que.length(); q++) {

                                        JSONObject a = Que.getJSONObject(q);

                                        Question = a.getString("text");
                                        Log.e(TAG, "Question:  " + Question);

                                        CurrentQuestionNum = a.getString("order");
                                        Log.e(TAG, "CurrentQuestionNum:  " + CurrentQuestionNum);

                                        JSONObject ImageUrlAnserw = (JSONObject) a.get("image");

                                        ImageUrlCat = ImageUrlAnserw.get("url").toString();

                                        if (a.isNull("url")) {

                                            ImageUrlOdp = "0";

                                        } else {

                                            ImageUrlOdp = a.get("url").toString();
                                            Log.e(TAG, " ImageUrlOdp           : " + ImageUrlOdp);
                                        }

                                        JSONArray Ans = a.getJSONArray("answers");

                                        Log.e(TAG, "Odpowiedzi:  " + Ans);

                                        int n;
                                        for (n = 0; n < Ans.length(); n++) {
                                            JSONObject v = Ans.getJSONObject(n);

                                            Answer = v.get("text").toString();
                                            Log.e(TAG, "  Answer:   " + Answer);

                                            AnswerNum = v.getString("order");
                                            Log.e(TAG, "AnswerNum:  " + AnswerNum);


                                            if (v.isNull("isCorrect")) {

                                                QuestionResult = "0";
                                                Log.e(TAG, "Correct:   " + QuestionResult);

                                            } else {

                                                QuestionResult = v.getString("isCorrect");
                                                Log.e(TAG, "Correct:   " + QuestionResult);
                                            }

                                            Log.e("wczytanie", "IdQue:   " + IdQue);
                                            Log.e("wczytanie", "CurrentQuestionNum:   " + CurrentQuestionNum);
                                            Log.e("wczytanie", "Question:   " + Question);
                                            Log.e("wczytanie", "Answer:   " + Answer);
                                            Log.e("wczytanie", "QuestionResult:   " + QuestionResult);
                                            Log.e("wczytanie", "ImageUrlOdp:   " + ImageUrlOdp);
                                            Log.e("wczytanie", "AnswerNum:   " + AnswerNum);

                                            Log.e("wczytanie", "-----------------------------------------------------" );

                                            databaseAnswer.addAnswer(new AnswerObject(IdQue, Question, Answer, QuestionResult, ImageUrlOdp, CurrentQuestionNum, AnswerNum));
                                        }
                                    }
                                } catch (final JSONException e) {
                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),
                                                    "Json parsing error: " + e.getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                            } else {
                                Log.e(TAG, "Couldn't get json from server.");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),
                                                "Couldn't get json from server. Check LogCat for possible errors!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            TitleQue = c.getString("title");
                            Log.e(TAG, "TitleQue: " + TitleQue);

                            QuestionNum = c.getString("questions");
                            Log.e(TAG, "Liczba pytań " + QuestionNum);

                            JSONObject ImageQuiz = (JSONObject) c.get("mainPhoto");

                            ImageUrlCat = ImageQuiz.get("url").toString();
                            Log.e(TAG, " Adres Url zdjecia: " + ImageUrlCat);

                            //Dodanie zmeinnych do Obiektu (nazwy musza byc takie same jak w Objekcie
                            QuestionObject object = new QuestionObject(IdQue, TitleQue, ImageUrlCat, QuestionNum);

                            databaseQuestion.addQuestion(new QuestionObject(IdQue, TitleQue, ImageUrlCat, QuestionNum));

                            //Metoda dodawania do Objektu
                            resoult.add(object);

                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }else if (netInfo == null){


                Cursor res = databaseQuestion.getAllQuestionData();

                while (res.moveToNext()) {
                    QuestionObject object = new QuestionObject(res.getString(0), res.getString(1), res.getString(2), res.getString(3));

                    //Metoda dodawania do Objektu
                    resoult.add(object);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Metoda notujaca zmiany (Wywoluje zapisanie zmiennych)
            mCategoryAdapter.notifyDataSetChanged();

            //Zatrzymanie animacji wyszukiwania
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}