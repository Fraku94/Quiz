package com.example.fraku.quiz;


import android.content.Context;
import android.database.Cursor;
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

import com.example.fraku.quiz.Category.CategoryAdapter;
import com.example.fraku.quiz.Database.DatabaseHandlerCategory;
import com.example.fraku.quiz.Database.DatabaseHandlerQuestion;
import com.example.fraku.quiz.Object.CategoryObject;
import com.example.fraku.quiz.Object.QuestionObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private String IdCat, TytulCat, ImageUrlCat, LiczbaPyt, Pytanie, Odpowiedz, WynikOdp, ImageUrlOdp, NumerPyt, NumerOdp;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mCategoryAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutMenager;

    private DatabaseHandlerCategory databaseCategory;
    private DatabaseHandlerQuestion databaseQuestion;

    private  NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sprawdzenie dostępu do internetu
        isOnline();

        databaseCategory = new DatabaseHandlerCategory(this);
        databaseQuestion = new DatabaseHandlerQuestion(this);

        //Ustawienie RecycleView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);


        //Ustawienie Adaptera oraz LayoutMenagera. Uzycie Contextu fragmentu
        mCategoryLayoutMenager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mCategoryLayoutMenager);
        mCategoryAdapter = new CategoryAdapter(getDataCat(),getApplicationContext());
        mRecyclerView.setAdapter(mCategoryAdapter);



        //Przypisanie funkicji odswiezania
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.setRefreshing(true);

        //Inicjacja odswierzania
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isOnline();

                if (netInfo != null) {
                    clear();
                    new GetCategory().execute();

                    mCategoryAdapter = new CategoryAdapter(getDataCat(),getApplicationContext());
                    mRecyclerView.setAdapter(mCategoryAdapter);
                }else if (netInfo == null){
                    Toast.makeText(MainActivity.this,"Brak połączenia z interentem",Toast.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
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
    private ArrayList<CategoryObject> resoult = new ArrayList<CategoryObject>();


    private List<CategoryObject> getDataCat() {

        //uruchomienie getcategory
        new GetCategory().execute();

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

                HttpHandler sh = new HttpHandler();

                //Link do kategorii
                String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";
                String jsonStr = sh.makeServiceCall(url);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // JSON Array kategorii
                        JSONArray kategoria = jsonObj.getJSONArray("items");

                        //Przejscie do JSONObject
                        JSONObject resultObject = (JSONObject) kategoria.get(0);

                        // Pobieranie
                        for (int i = 0; i < kategoria.length(); i++) {
                            JSONObject c = kategoria.getJSONObject(i);

                            //ID kategorii
                            IdCat = c.getString("id");

                            //Link do szczegołów
                            String urlq = "http://quiz.o2.pl/api/v1/quiz/"+IdCat+"/0";

                            String jsonStrPyt = sh.makeServiceCall(urlq);

                            if (jsonStrPyt != null) {
                                try {
                                    JSONObject jsonObjPyt = new JSONObject(jsonStrPyt);

                                    // JSON Array pytań
                                    JSONArray Pytania = jsonObjPyt.getJSONArray("questions");

                                    JSONObject resultObjectPyt = (JSONObject) Pytania.get(0);

                                    JSONArray Odpowiedzi = resultObjectPyt.getJSONArray("answers");

                                    Log.e(TAG, "Odpowiedzi:  " + Odpowiedzi);

                                    // Pobieranie danych kategorii
                                    for (int q = 0; q < Pytania.length(); q++) {
                                        JSONObject a = Pytania.getJSONObject(q);

                                        Pytanie = a.getString("text");
                                        Log.e(TAG, "Pytanie:  " + Pytanie);

                                        NumerPyt = a.getString("order");
                                        Log.e(TAG, "NumerPyt:  " + NumerPyt);

                                        for (int n = 0; n < Odpowiedzi.length(); n++) {
                                            JSONObject v = Odpowiedzi.getJSONObject(n);

                                            Odpowiedz = v.get("text").toString();
                                            Log.e(TAG, "  Odpowiedz:   " + Odpowiedz);

                                            NumerOdp = a.getString("order");
                                            Log.e(TAG, "NumerOdp:  " + NumerOdp);



                                            if (v.isNull("url")){

                                                ImageUrlOdp = "0";

                                            }else{

                                                ImageUrlOdp = v.get("url").toString();
                                                Log.e(TAG, " ImageUrlOdp           : " + ImageUrlOdp);

                                            }

                                            if (v.isNull("isCorrect")){

                                                WynikOdp = "0";
                                                Log.e(TAG, "Correct:   " + WynikOdp);

                                            }else{

                                                WynikOdp = v.getString("isCorrect");
                                                Log.e(TAG, "Correct:   " + WynikOdp);

                                            }


                                        }

//                                            for (int m = 0; m < image.length(); m++) {
//                                                JSONObject x = image.getJSONObject(m);
//
//                                                ImageUrlQue = x.get("url").toString();
//                                                Log.e(TAG, "  ImageUrlQue:   " + ImageUrlQue);
//
//                                            }
                                            //Dodanie zmeinnych do Obiektu (nazwy musza byc takie same jak w Objekcie

                                            databaseQuestion.addQuestion(new QuestionObject(IdCat, Pytanie, Odpowiedz, WynikOdp, ImageUrlOdp, NumerPyt, NumerOdp));

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

                            TytulCat = c.getString("title");
                            Log.e(TAG, "TytulCat: " + TytulCat);

                            LiczbaPyt = c.getString("questions");
                            Log.e(TAG, "Liczba pytań " + LiczbaPyt);

                            JSONObject getImage = (JSONObject) resultObject.get("mainPhoto");

                            ImageUrlCat = getImage.get("url").toString();
                            Log.e(TAG, " Adres Url zdjecia: " + ImageUrlCat);

                            //Dodanie zmeinnych do Obiektu (nazwy musza byc takie same jak w Objekcie
                            CategoryObject object = new CategoryObject(IdCat, TytulCat, ImageUrlCat, LiczbaPyt);

                            databaseCategory.addCategory(new CategoryObject(IdCat, TytulCat, ImageUrlCat, LiczbaPyt));

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


                Cursor res = databaseCategory.getAllData();

                while (res.moveToNext()) {
                    CategoryObject object = new CategoryObject(res.getString(0), res.getString(1), res.getString(2), res.getString(3));

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