package com.rakib.initialassessment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView questionImageView;

    Intent intent;
    String hasImage;

    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * TODO: appbar title should be the assessment name
         */

        id = getIntent().getLongExtra("id",-1);

        InitialAssessmentDbHelper dbHelper = new InitialAssessmentDbHelper(this);
        questionList = dbHelper.getAllQuestions(getIntent().getStringExtra("cat"));
        currentQ = questionList.get(qid);


        questionTextView = findViewById(R.id.question_name);
        questionImageView = findViewById(R.id.ques_image);
        optARadioButton = findViewById(R.id.radio_opt1);
        optBRadioButton = findViewById(R.id.radio_opt2);
        optCRadioButton = findViewById(R.id.radio_opt3);
        nextButton = findViewById(R.id.fabNext);

        hasImage = getIntent().getStringExtra("has_image");
        if (hasImage.equals("yes"))
            setQuestionView(0);
        else
            setQuestionView(1);

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
                        Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
                        View v = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                        v.getBackground().setColorFilter(Color.parseColor("#2ecc71"), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
                        TextView text = view.findViewById(android.R.id.message);
                        //text.setTextColor(YOUR_TEXT_COLOUR);

                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Wrong! Correct answer is " + currentQ.getCorrect(), Toast.LENGTH_SHORT);
                        View v = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                        v.getBackground().setColorFilter(Color.parseColor("#e74c3c"), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
                        TextView text = view.findViewById(android.R.id.message);
                        //text.setTextColor(YOUR_TEXT_COLOUR);

                        toast.show();
                    }
                    if (qid < 5) {
                        currentQ = questionList.get(qid);
                        if (hasImage.equals("yes"))
                            setQuestionView(0);
                        else
                            setQuestionView(1);
                    } else {
                        intent = new Intent(getApplicationContext(), CatagoryResultActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("category", currentQ.getCategory());
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Make a choice first!", Toast.LENGTH_SHORT);
                    View v = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
                    v.getBackground().setColorFilter(Color.parseColor("#12CBC4"), PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
                    TextView text = view.findViewById(android.R.id.message);
                    //text.setTextColor(YOUR_TEXT_COLOUR);

                    toast.show();
                    //Toast.makeText(getApplicationContext(), "Make a choice first!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void setQuestionView(int i) {

        if (i == 0) {

            questionTextView.setText("identify this : ");

            Resources res = getResources();
            String mDrawableName = currentQ.getQuestion();
            int resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
            questionImageView.setImageResource(resID);

            questionImageView.getLayoutParams().height = 900;
            questionImageView.requestLayout();
        } else {
            questionTextView.setText(currentQ.getQuestion());
        }

        optARadioButton.setText(currentQ.getOptionA());
        optBRadioButton.setText(currentQ.getOptionB());
        optCRadioButton.setText(currentQ.getOptionC());
        qid++;
    }

}
