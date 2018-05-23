package com.example.fraku.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizEndActivity extends AppCompatActivity {

    private TextView mPercent;

    private Button mAgain, mEnd;

    private String CatId, QuestionNum, Percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        mPercent = (TextView) findViewById(R.id.procentTV);
        mAgain = (Button) findViewById(R.id.againQuiz);
        mEnd = (Button) findViewById(R.id.endQuiz);

        CatId = getIntent().getExtras().getString("CategoryId");
        QuestionNum = getIntent().getExtras().getString("CategoryQuestionNumber");
        Percent = getIntent().getExtras().getString("Percent");

        mPercent.setText(Percent + " %");

        mEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainIntent);
                finish();
                return;
            }
        });

        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent againIntent = new Intent(getApplicationContext(),QuizActivity.class);
                Bundle b = new Bundle();
                b.putString("CategoryId", CatId);
                b.putString("CategoryQuestionNumber", QuestionNum);
                againIntent.putExtras(b);
                startActivity(againIntent);
                finish();
                return;
            }
        });
    }
}
