package com.example.fraku.quiz.Category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fraku.quiz.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mResult;
    public ImageView mImage;
    public String mCategoryId;

    public CategoryViewHolder(View itemView) {
        super(itemView);


        //Tu wpisujesz wszystkie TextView,ImageView,itp jakie dodajesz w item_....xml (tutaj item_liked.xml)
        mTitle = itemView.findViewById(R.id.Title);
        mResult = itemView.findViewById(R.id.Result);

        mImage = itemView.findViewById(R.id.Image);
    }


    @Override
    public void onClick(View view) {

    }
}