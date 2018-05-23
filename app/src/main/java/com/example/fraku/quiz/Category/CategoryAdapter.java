package com.example.fraku.quiz.Category;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fraku.quiz.Database.DatabaseHandlerStats;
import com.example.fraku.quiz.Object.QuestionObject;
import com.example.fraku.quiz.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private List<QuestionObject> CategoryList;
    private Context context;
    private DatabaseHandlerStats databaseStats;

    //Przypisanie Obiektów do adaptera
    public CategoryAdapter(List<QuestionObject> CategoryList, Context context){
        this.CategoryList = CategoryList;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Przypisanie wygladu okna do adaptera
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null,false);

        //Ustawienie RecycleView
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        //Podpienie i zwrocenie wygladu
        CategoryViewHolder rcv = new CategoryViewHolder((layoutView));

        return rcv;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {


        databaseStats = new DatabaseHandlerStats(context);
        Cursor resResult = databaseStats.getResultStats(CategoryList.get(position).getIdQue());

        if(resResult.moveToPosition(0)) {

            holder.mQuizResult = resResult.getString(0);
            holder.mQuestionNumCurrent = resResult.getInt(1);
            holder.mQuestionNum = Integer.parseInt(CategoryList.get(position).getQuestionNum());

            if (holder.mQuestionNumCurrent < holder.mQuestionNum){

                holder.mResult.setText("Ostatni wynik : " + holder.mQuestionNum + "/" + holder.mQuestionNumCurrent  + " Procent : " + holder.mQuizResult + "%/100%");

            }else {

                holder.mResult.setText("Quzi rozwiązany w :" + holder.mQuizResult + "%/100%");
            }
        }

        holder.mCategoryId = CategoryList.get(position).getIdQue();
        holder.mCategoryQuestionNumber = CategoryList.get(position).getQuestionNum();

        holder.mTitle.setText(CategoryList.get(position).getTitleQue());


        //Sprawdzenie czy wartosc linku to "default" jesli nie ma załadować link i podpiac zdjecie ImageView
        Glide.with(context).load(CategoryList.get(position).getImageUrl()).into(holder.mImage);

//        String url = CategoryList.get(position).getImageUrl();
//        BitmapFactory.Options bmOptions;
//        bmOptions = new BitmapFactory.Options();
//        bmOptions.inSampleSize = 1;
//        Bitmap bm = loadBitmap(url, bmOptions);
//        holder.mImage.setImageBitmap(bm);


    }

    @Override
    public int getItemCount() {
        return this.CategoryList.size();
    }


//    public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
//        Bitmap bitmap = null;
//        InputStream in = null;
//        try {
//            in = OpenHttpConnection(URL);
//            bitmap = BitmapFactory.decodeStream(in, null, options);
//            in.close();
//        } catch (IOException e1) {
//        }
//        return bitmap;
//    }
//
//    private static InputStream OpenHttpConnection(String strURL)
//            throws IOException {
//        InputStream inputStream = null;
//        URL url = new URL(strURL);
//        URLConnection conn = url.openConnection();
//
//        try {
//            HttpURLConnection httpConn = (HttpURLConnection) conn;
//            httpConn.setRequestMethod("GET");
//            httpConn.connect();
//
//            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                inputStream = httpConn.getInputStream();
//            }
//        } catch (Exception ex) {
//        }
//        return inputStream;
//    }
}
