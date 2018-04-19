package com.example.fraku.quiz.Category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.CategoryList.size();
    }
}
