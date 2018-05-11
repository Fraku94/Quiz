package com.example.fraku.quiz.Category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fraku.quiz.QuizActivity;
import com.example.fraku.quiz.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mResult;
    public ImageView mImage;
    public String mCategoryId, mCategoryQuestionNumber;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


        //Tu wpisujesz wszystkie TextView,ImageView,itp jakie dodajesz w item_....xml (tutaj item_liked.xml)
        mTitle = itemView.findViewById(R.id.Title);
        mResult = itemView.findViewById(R.id.Result);

        mImage = itemView.findViewById(R.id.Image);
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext() , QuizActivity.class);
        Bundle b = new Bundle();
        b.putString("CategoryId", mCategoryId);
        b.putString("CategoryQuestionNumber", mCategoryQuestionNumber);
        intent.putExtras(b);
        view.getContext().startActivity(intent);

    }
}