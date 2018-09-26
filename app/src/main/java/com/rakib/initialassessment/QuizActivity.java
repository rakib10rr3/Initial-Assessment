package com.rakib.initialassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.initialassessment.data.InitialAssessmentDbHelper;
import com.rakib.initialassessment.model.Question;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    List<Question> questionList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView questionTextView;
    RadioButton optARadioButton, optBRadioButton, optCRadioButton;
    FloatingActionButton nextButton;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * TODO: appbar title should be the assessment name
         */

        InitialAssessmentDbHelper dbHelper = new InitialAssessmentDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        currentQ = questionList.get(qid);

        questionTextView = findViewById(R.id.question_name);
        optARadioButton = findViewById(R.id.radio_opt1);
        optBRadioButton = findViewById(R.id.radio_opt2);
        optCRadioButton = findViewById(R.id.radio_opt3);
        nextButton = findViewById(R.id.fabNext);

        setQuestionView();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = findViewById(R.id.radio_group_ques);

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    RadioButton answer = findViewById(radioGroup.getCheckedRadioButtonId());

                    radioGroup.clearCheck();
                    if (currentQ.getCorrect().equals(answer.getText())) {
                        score++;
                        Log.d("Score", String.valueOf(score));
                    }
                    if (qid < 5) {
                        currentQ = questionList.get(qid);
                        setQuestionView();
                    } else {
                        intent = new Intent(getApplicationContext(), CatagoryResultActivity.class);
                        intent.putExtra("category", currentQ.getCategory());
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Make a choice first!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setQuestionView() {
        questionTextView.setText(currentQ.getQuestion());
        optARadioButton.setText(currentQ.getOptionA());
        optBRadioButton.setText(currentQ.getOptionB());
        optCRadioButton.setText(currentQ.getOptionC());
        qid++;
    }

}
