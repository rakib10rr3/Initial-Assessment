package com.rakib.initialassessment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    String category;

    Long id;
    int flag;
    int assessmentNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        id = getIntent().getLongExtra("id", -1);
        flag = getIntent().getIntExtra("date_flag", -1);
        assessmentNo = getIntent().getIntExtra("assessment_no", -1);
        category = getIntent().getStringExtra("cat");

        getSupportActionBar().setTitle(category);

        if (category.equals("Matching"))
            getSupportActionBar().setTitle("Matching to Sample");
        if (category.equals("Receptive by FFC"))
            getSupportActionBar().setTitle("Receptive by Function, Feature & Class");

        InitialAssessmentDbHelper dbHelper = new InitialAssessmentDbHelper(this);
        questionList = dbHelper.getAllQuestions(category);
        currentQ = questionList.get(qid);


        questionTextView = findViewById(R.id.question_name);
        questionImageView = findViewById(R.id.ques_image);
        optARadioButton = findViewById(R.id.radio_opt1);
        optBRadioButton = findViewById(R.id.radio_opt2);
        optCRadioButton = findViewById(R.id.radio_opt3);
        nextButton = findViewById(R.id.fabNext);

        hasImage = getIntent().getStringExtra("has_image");
        if (hasImage.equals("yes"))
            setQuestionView(0, category);
        else
            setQuestionView(1, category);

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
                            setQuestionView(0, category);
                        else
                            setQuestionView(1, category);
                    } else {
                        intent = new Intent(getApplicationContext(), CatagoryResultActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("category", currentQ.getCategory());
                        intent.putExtra("score", score);
                        intent.putExtra("date_flag", flag);
                        intent.putExtra("assessment_no", assessmentNo);
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

    private void setQuestionView(int i, String category) {

        if (i == 0) {

            if (category.equals("Matching"))
                questionTextView.setText("Match with this : ");
            else if (category.equals("Labeling"))
                questionTextView.setText("Identify this : ");
            else if (category.equals("Letters and Numbers"))
                questionTextView.setText("What is this? ");


            Resources res = getResources();
            String mDrawableName = currentQ.getQuestion();
            int resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
            questionImageView.setImageResource(resID);

            questionImageView.getLayoutParams().height = 900;
            questionImageView.requestLayout();
        } else {
            questionTextView.setText(currentQ.getQuestion());
        }


        if (category.equals("Matching")) {


            Resources res = getResources();

            String mDrawableName1 = currentQ.getOptionA();
            int resID1 = res.getIdentifier(mDrawableName1, "drawable", getPackageName());

            String mDrawableName2 = currentQ.getOptionB();
            int resID2 = res.getIdentifier(mDrawableName2, "drawable", getPackageName());

            String mDrawableName3 = currentQ.getOptionC();
            int resID3 = res.getIdentifier(mDrawableName3, "drawable", getPackageName());

            //questionImageView.setImageResource(resID);

            optARadioButton.setText(currentQ.getOptionA());
            optBRadioButton.setText(currentQ.getOptionB());
            optCRadioButton.setText(currentQ.getOptionC());

            optARadioButton.setTextColor(Color.TRANSPARENT);
            optBRadioButton.setTextColor(Color.TRANSPARENT);
            optCRadioButton.setTextColor(Color.TRANSPARENT);

            optARadioButton.setCompoundDrawablesWithIntrinsicBounds(resID1, 0, 0, 0);
            optBRadioButton.setCompoundDrawablesWithIntrinsicBounds(resID2, 0, 0, 0);
            optCRadioButton.setCompoundDrawablesWithIntrinsicBounds(resID3, 0, 0, 0);


        } else {
            optARadioButton.setText(currentQ.getOptionA());
            optBRadioButton.setText(currentQ.getOptionB());
            optCRadioButton.setText(currentQ.getOptionC());
        }

        qid++;
    }

}
