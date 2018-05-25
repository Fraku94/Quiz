package com.example.fraku.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fraku.quiz.Database.DatabaseHandlerAnswer;
import com.example.fraku.quiz.Database.DatabaseHandlerStats;
import com.example.fraku.quiz.Object.StatsObject;

public class QuizActivity extends AppCompatActivity {

    private String TAG = QuizActivity.class.getSimpleName();

    private String CatId;

    private  int QuestionNum, CurrentQuestion, GoodAnswer, BadAnswer, Percent, SaveQuestionNum;

    private String[] Question = new String[20];

    private int[] QuestionResult = new int[20];

    private RadioGroup mRadioGroup;

    private ProgressBar mProgresBar;

    private RadioButton mQ1, mQ2, mQ3, mQ4;

    private TextView mTextView;

    private DatabaseHandlerAnswer databaseQuestion;
    private DatabaseHandlerStats databaseStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        databaseQuestion = new DatabaseHandlerAnswer(this);
        databaseStats = new DatabaseHandlerStats(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mProgresBar = (ProgressBar) findViewById(R.id.progressBar);
        mQ1 = (RadioButton) findViewById(R.id.q1);
        mQ2 = (RadioButton) findViewById(R.id.q2);
        mQ3 = (RadioButton) findViewById(R.id.q3);
        mQ4 = (RadioButton) findViewById(R.id.q4);
        mTextView = (TextView) findViewById(R.id.Title);

        CatId = getIntent().getExtras().getString("CategoryId");
        QuestionNum = Integer.parseInt(getIntent().getExtras().getString("CategoryQuestionNumber"));

        mProgresBar.setMax(QuestionNum);

        CurrentQuestion = 1;
        GoodAnswer = 0;
        BadAnswer = 0;

        Cursor resSave = databaseStats.getAllStatsData(CatId);

        if(resSave.moveToPosition(0)) {

            SaveQuestionNum = resSave.getInt(1);
            if (SaveQuestionNum != 1){

                CurrentQuestion = SaveQuestionNum;
                GoodAnswer = resSave.getInt(2);
                BadAnswer = resSave.getInt(3);

                mProgresBar.setProgress(CurrentQuestion);
            }
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int checkedRadioButtonId = mRadioGroup.getCheckedRadioButtonId();

                if (checkedRadioButtonId == R.id.q1) {

                    mQ1.setChecked(false);

                    if (CurrentQuestion <= QuestionNum){

                        if (QuestionResult[0] == 1){

                            Toast.makeText(getApplicationContext(),"Dobra odpowiedź :)",Toast.LENGTH_SHORT).show();
                            GoodAnswer++;

                        }else {

                            Toast.makeText(getApplicationContext(),"Zła odpowiedź :(",Toast.LENGTH_SHORT).show();
                            BadAnswer++;
                        }
                        mProgresBar.setProgress(CurrentQuestion);
                        CurrentQuestion++;
                        GetQuestion();
                    }
                }
                if (checkedRadioButtonId == R.id.q2) {

                    mQ2.setChecked(false);

                    if (CurrentQuestion <= QuestionNum){

                        if (QuestionResult[1] == 1){

                            Toast.makeText(getApplicationContext(),"Dobra odpowiedź :)",Toast.LENGTH_SHORT).show();
                            GoodAnswer++;

                        }else {

                            Toast.makeText(getApplicationContext(),"Zła odpowiedź :(",Toast.LENGTH_SHORT).show();
                            BadAnswer++;
                        }
                        mProgresBar.setProgress(CurrentQuestion);
                        CurrentQuestion++;
                        GetQuestion();
                    }
                }
                if (checkedRadioButtonId == R.id.q3) {

                    mQ3.setChecked(false);

                    if (CurrentQuestion <= QuestionNum){

                        if (QuestionResult[2] == 1){

                            Toast.makeText(getApplicationContext(),"Dobra odpowiedź :)",Toast.LENGTH_SHORT).show();
                            GoodAnswer++;

                        }else {

                            Toast.makeText(getApplicationContext(),"Zła odpowiedź :(",Toast.LENGTH_SHORT).show();
                            BadAnswer++;
                        }
                        mProgresBar.setProgress(CurrentQuestion);
                        CurrentQuestion++;
                        GetQuestion();
                    }
                }
                if (checkedRadioButtonId == R.id.q4) {

                    mQ4.setChecked(false);

                    if (CurrentQuestion <= QuestionNum){

                        if (QuestionResult[3] == 1){

                            Toast.makeText(getApplicationContext(),"Dobra odpowiedź :)",Toast.LENGTH_SHORT).show();
                            GoodAnswer++;

                        }else {

                            Toast.makeText(getApplicationContext(),"Zła odpowiedź :(",Toast.LENGTH_SHORT).show();
                            BadAnswer++;
                        }
                        mProgresBar.setProgress(CurrentQuestion);
                        CurrentQuestion++;
                        GetQuestion();
                    }
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (CurrentQuestion <= QuestionNum){
            Percent = (CurrentQuestion *100)/ QuestionNum;
            databaseStats.removeSaveQuiz(CatId);
            databaseStats.addStats(new StatsObject(CatId, CurrentQuestion, GoodAnswer, BadAnswer, Percent));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (CurrentQuestion <= QuestionNum){
            Percent = (CurrentQuestion *100)/ QuestionNum;
            databaseStats.removeSaveQuiz(CatId);
            databaseStats.addStats(new StatsObject(CatId, CurrentQuestion, GoodAnswer, BadAnswer, Percent));
        }

        Intent endIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(endIntent);
        finish();
        return;

    }

    @Override
    protected void onStart() {

        super.onStart();
        GetQuestion();

    }

    private void GetQuestion() {

        if (CurrentQuestion <= QuestionNum){

            final Cursor resQue = databaseQuestion.getAnswer(CatId, CurrentQuestion);

            for (int i=0;i<resQue.getCount();i++){

                if(resQue.moveToPosition(i)) {

                    mTextView.setText(resQue.getString(0));
                    Question[i] = resQue.getString(1);
                    QuestionResult[i] = Integer.parseInt(resQue.getString(2));
                }

                mQ1.setText( Question[0]);
                mQ2.setText( Question[1]);
                mQ3.setText( Question[2]);
                mQ4.setText( Question[3]);

                if (mQ1.getText() == ""){
                    mQ1.setVisibility(View.INVISIBLE);
                }else{

                    mQ1.setVisibility(View.VISIBLE);
                    mQ1.setText( Question[0]);
                }

                if (mQ2.getText() == ""){
                    mQ2.setVisibility(View.INVISIBLE);
                }else{

                    mQ2.setVisibility(View.VISIBLE);
                    mQ2.setText( Question[1]);
                }

                if (mQ3.getText() == ""){
                    mQ3.setVisibility(View.INVISIBLE);
                }else{

                    mQ3.setVisibility(View.VISIBLE);
                    mQ3.setText( Question[2]);
                }

                if (mQ4.getText() == ""){
                    mQ4.setVisibility(View.INVISIBLE);
                }else{

                    mQ4.setVisibility(View.VISIBLE);
                    mQ4.setText( Question[3]);
                }
            }

        }else {

            EndQuiz();
        }
    }

    private void EndQuiz(){

        Percent = (GoodAnswer *100)/ QuestionNum;

        Log.e(TAG, "GoodAnswer      :   " + GoodAnswer);
        Log.e(TAG, "BadAnswer       :   " + BadAnswer);
        Log.e(TAG, "Percent         :   " + Percent);

        Toast.makeText(getApplicationContext(),"Koniec Quizu twoj wynik to : " + Percent + " procent.",Toast.LENGTH_SHORT).show();

        databaseStats.removeSaveQuiz(CatId);
        databaseStats.addStats(new StatsObject(CatId, QuestionNum, GoodAnswer, BadAnswer, Percent));

        Intent endIntent = new Intent(getApplicationContext(),QuizEndActivity.class);
        Bundle b = new Bundle();
        b.putString("CategoryId", CatId);
        b.putString("CategoryQuestionNumber", Integer.toString(QuestionNum));
        b.putString("Percent", Integer.toString(Percent));
        endIntent.putExtras(b);
        startActivity(endIntent);
        finish();
        return;

    }
}