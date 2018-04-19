package com.example.fraku.quiz;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.fraku.quiz.Category.CategoryAdapter;
import com.example.fraku.quiz.Category.CategoryObject;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mCategoryAdapter;
    private RecyclerView.LayoutManager mCategoryLayoutMenager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Ustawienie RecycleView
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.setHasFixedSize(true);
//
//
//        //Ustawienie Adaptera oraz LayoutMenagera. Uzycie Contextu fragmentu
//        mCategoryLayoutMenager = new LinearLayoutManager(getApplicationContext());
//        mRecyclerView.setLayoutManager(mCategoryLayoutMenager);
//        mCategoryAdapter = new CategoryAdapter(getDataSet(),getApplicationContext());
//        mRecyclerView.setAdapter(mCategoryAdapter);
//
//
//
//        //Przypisanie funkicji odswiezania
//        swipeRefreshLayout = findViewById(R.id.swipeContainer);
//
//        //Inicjacja odswierzania
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {

                new WebServiceHandler()
                        .execute("http://quiz.o2.pl/api/v1/quizzes/0/1");
//                mCategoryAdapter = new CategoryAdapter(getDataSet(),getApplicationContext());
//                mRecyclerView.setAdapter(mCategoryAdapter);
//
//            }
//        });
//
//        //Style kolka odswiezania
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//
//        //Czyszczenie fragmentu gdy jest automatycznie przesowany

    }

    //Czyszczenie fragmentu
//    private void clear() {
//        int size = this.resoult.size();
//        this.resoult.clear();
//        mCategoryAdapter.notifyItemRangeChanged(0, size);
//    }

//    //Przeslanie do Adaptera Rezultatow
//    private ArrayList<CategoryObject> resoult = new ArrayList<CategoryObject>();
//
//    private List<CategoryObject> getDataSet() {
//        //Tu Startuje fragment
//        new WebServiceHandler()
//                .execute("http://quiz.o2.pl/api/v1/quizzes/0/1/");
//        return resoult;
//    }


    private class WebServiceHandler extends AsyncTask<String, Void, String> {

        // okienko dialogowe, które każe użytkownikowi czekać
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        // metoda wykonywana jest zaraz przed główną operacją (doInBackground())
        // mamy w niej dostęp do elementów UI
        @Override
        protected void onPreExecute() {
            // wyświetlamy okienko dialogowe każące czekać
            dialog.setMessage("Czekaj...");
            dialog.show();
        }

        // główna operacja, która wykona się w osobnym wątku
        // nie ma w niej dostępu do elementów UI
        @Override
        protected String doInBackground(String... urls) {

            try {
                // zakładamy, że jest tylko jeden URL
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();

                // pobranie danych do InputStream
                InputStream in = new BufferedInputStream(
                        connection.getInputStream());

                // konwersja InputStream na String
                // wynik będzie przekazany do metody onPostExecute()
                return streamToString(in);

            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
                return null;
            }

        }

        // metoda wykonuje się po zakończeniu metody głównej,
        // której wynik będzie przekazany;
        // w tej metodzie mamy dostęp do UI
        @Override
        protected void onPostExecute(String result) {

            // chowamy okno dialogowe
            dialog.dismiss();

            try {
                // reprezentacja obiektu JSON w Javie
                JSONObject json = new JSONObject(result);

                String Id = "";
                String Name = "";
                String ImageUrl = "";

                // pobranie pól obiektu JSON i wyświetlenie ich na ekranie
                     Id = json.optString("count");
                     Name = json.optString("title");
                     ImageUrl = json.optString("url");
                Log.e("TAG", "Dane:  "+ Id + " : " + Name + " : " + ImageUrl);
                //Dodanie zmeinnych do Obiektu (nazwy musza byc takie same jak w Objekcie
//                CategoryObject object = new CategoryObject(Id, Name, ImageUrl);
//
//                //Metoda dodawania do Objektu
//                resoult.add(object);
//
//                //Metoda notujaca zmiany (Wywoluje zapisanie zmiennych)
//                mCategoryAdapter.notifyDataSetChanged();
//
//                //Zatrzymanie animacji wyszukiwania
//                swipeRefreshLayout.setRefreshing(false);

            } catch (Exception e) {
                // obsłuż wyjątek
                Log.d(MainActivity.class.getSimpleName(), e.toString());
            }
        }
    }

    // konwersja z InputStream do String
    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

        } catch (IOException e) {
            // obsłuż wyjątek
            Log.d(MainActivity.class.getSimpleName(), e.toString());
        }

        return stringBuilder.toString();
    }

}
