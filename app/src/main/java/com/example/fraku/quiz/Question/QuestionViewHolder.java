package com.example.fraku.quiz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fraku.quiz.QuizActivity;
import com.example.fraku.quiz.QuizEndActivity;
import com.example.fraku.quiz.R;

public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mTitle, mResult;
    public ImageView mImage;
    public String mQuestionId, mCategoryQuestionNumber, mQuizResult;
    public int mQuestionNumCurrent, mQuestionNum, mQuizGoodAnswer;

    public QuestionViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mTitle = itemView.findViewById(R.id.Title);
        mResult = itemView.findViewById(R.id.Result);
        mImage = itemView.findViewById(R.id.Image);
    }

    @Override
    public void onClick(View view) {

        if (mQuestionNumCurrent == mQuestionNum){

            Intent intent = new Intent(view.getContext() , QuizActivity.class);
            Bundle b = new Bundle();
            b.putString("CategoryId", mQuestionId);
            b.putString("CategoryQuestionNumber", mCategoryQuestionNumber);
            b.putString("Percent", mQuizResult);
            intent.putExtras(b);
            view.getContext().startActivity(intent);

        }else {

            Intent intent = new Intent(view.getContext() , QuizEndActivity.class);
            Bundle b = new Bundle();
            b.putString("CategoryId", mQuestionId);
            b.putString("CategoryQuestionNumber", mCategoryQuestionNumber);
            b.putString("Percent", mQuizResult);
            intent.putExtras(b);
            view.getContext().startActivity(intent);

        }
    }
}