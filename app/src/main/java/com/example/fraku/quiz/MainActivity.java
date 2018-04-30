package com.example.fraku.quiz;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.fraku.quiz.Category.CategoryAdapter;
import com.example.fraku.quiz.Category.CategoryObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private  String Id, Title, Questions, ImageUrl;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mCategoryAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutMenager;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



                //Ustawienie RecycleView
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);


        //Ustawienie Adaptera oraz LayoutMenagera. Uzycie Contextu fragmentu
        mCategoryLayoutMenager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mCategoryLayoutMenager);
        mCategoryAdapter = new CategoryAdapter(getDataSet(),getApplicationContext());
        mRecyclerView.setAdapter(mCategoryAdapter);



        //Przypisanie funkicji odswiezania
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        //Inicjacja odswierzania
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clear();
                new GetCategory().execute();

                mCategoryAdapter = new CategoryAdapter(getDataSet(),getApplicationContext());
                mRecyclerView.setAdapter(mCategoryAdapter);

            }
        });
    }


    //Czyszczenie fragmentu
    private void clear() {
        int size = this.resoult.size();
        this.resoult.clear();
        mCategoryAdapter.notifyItemRangeChanged(0, size);
    }

    //Przeslanie do Adaptera Rezultatow
    private ArrayList<CategoryObject> resoult = new ArrayList<CategoryObject>();

    private List<CategoryObject> getDataSet() {

        new GetCategory().execute();

        return resoult;
    }

    private class GetCategory extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


                String url = "http://quiz.o2.pl/api/v1/quizzes/0/100";
                String jsonStr = sh.makeServiceCall(url);

                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // JSON Array
                        JSONArray items = jsonObj.getJSONArray("items");

                        JSONObject resultObject = (JSONObject) items.get(0);

                        // Pobieranie danych kategorii
                        for (int i = 0; i < items.length(); i++) {
                            JSONObject c = items.getJSONObject(i);
                            Id = c.getString("id");
                            Log.e(TAG, "ID: " + Id);

                            Title = c.getString("title");
                            Log.e(TAG, "Tytuł: " + Title);

                            Questions = c.getString("questions");
                            Log.e(TAG, "Liczba pytań " + Questions);

                            JSONObject getImage = (JSONObject) resultObject.get("mainPhoto");

                            ImageUrl = getImage.get("url").toString();
                            Log.e(TAG, " Adres Url zdjecia: " + ImageUrl);

                            //Dodanie zmeinnych do Obiektu (nazwy musza byc takie same jak w Objekcie
                            CategoryObject object = new CategoryObject(Id, Title, ImageUrl, Questions);

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