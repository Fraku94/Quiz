package com.example.fraku.quiz.Category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.fraku.quiz.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private List<CategoryObject> CategoryList;
    private Context context;


    //Przypisanie Obiektów do adaptera
    public CategoryAdapter(List<CategoryObject> CategoryList, Context context){
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


        holder.mTitle.setText(CategoryList.get(position).getName());
        holder.mResult.setText(CategoryList.get(position).getId());

        //Sprawdzenie czy wartosc linku to "default" jesli nie ma załadować link i podpiac zdjecie ImageView
        Glide.with(context).load(CategoryList.get(position).getImageUrl()).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return this.CategoryList.size();
    }
}
