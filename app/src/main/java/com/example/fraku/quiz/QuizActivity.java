package com.example.fraku.quiz;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.fraku.quiz.Database.DatabaseHandlerQuestion;

public class QuizActivity extends AppCompatActivity {

    private String TAG = QuizActivity.class.getSimpleName();

    private String CatId, Pytanie;

    private  int LiczbaPytan, NumerOdp, NumerPyt, ObecnaOdp, ObecnePyt, i;

    private String[] Odp = new String[20];

    private String[] WynikOdp = new String[20];

    private  int[] NumerObecnejOdp = new int[20];

    private RadioGroup mRadioGroup;

    private RadioButton mQ1, mQ2, mQ3, mQ4;

    private TextView mTextView;

    private DatabaseHandlerQuestion databaseQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        databaseQuestion = new DatabaseHandlerQuestion(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mQ1 = (RadioButton) findViewById(R.id.q1);
        mQ2 = (RadioButton) findViewById(R.id.q2);
        mQ3 = (RadioButton) findViewById(R.id.q3);
        mQ4 = (RadioButton) findViewById(R.id.q4);

        mTextView = (TextView) findViewById(R.id.Title);

        CatId = getIntent().getExtras().getString("CategoryId");

        LiczbaPytan = Integer.parseInt(getIntent().getExtras().getString("CategoryQuestionNumber"));

        ObecnaOdp = 1;
        ObecnePyt = 1;
        i=0;

        GetQuestion();
    }

    private void GetQuestion() {

        Cursor resQue = databaseQuestion.getQuestion(CatId, "1");

        mTextView.setText(resQue.getString(0 ));

        Log.e("TAG", "wiaddddd   " +resQue.getString(0 ) );
//
//        do {
//
//            Question[i] = resQue.getString(1);
//            Correct[i] = resQue.getString(2);
//            i++;
//            CurrentQuestion = Integer.parseInt(resQue.getString(4));
//            OrderQuestion[i] = CurrentQuestion;
//            resQue.moveToNext();
//
//        } while (  OrderQuestion[i-1] >  OrderQuestion[i] );
//
//        mQ1.setText( Question[0]);
//        mQ2.setText( Question[1]);
//        mQ3.setText( Question[2]);
//        mQ4.setText( Question[3]);
//
//        CurrentAnserw = Integer.parseInt(resQue.getString(3));
//
//
//        if (mRadioGroup.isSelected()){
//
//            i=0;
//
//            if (OrderAnserw != AnserwNumber){
//                OrderAnserw = OrderAnserw + 1;
//                Anserw = Integer.toString(OrderAnserw);
//
//                resQue.moveToNext();
//                mTextView.setText(resQue.getString(0 ));
//
//                do {
//
//                    Question[i] = resQue.getString(1);
//                    Correct[i] = resQue.getString(2);
//                    i++;
//                    CurrentQuestion = Integer.parseInt(resQue.getString(4));
//                    OrderQuestion[i] = CurrentQuestion;
//                    resQue.moveToNext();
//
//                } while (  OrderQuestion[i-1] >  OrderQuestion[i] );
//
//                mQ1.setText( Question[0]);
//                mQ2.setText( Question[1]);
//                mQ3.setText( Question[2]);
//                mQ4.setText( Question[3]);
//
//
//            }
//        }
    }
}