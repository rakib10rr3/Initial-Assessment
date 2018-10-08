package com.rakib.initialassessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rakib.initialassessment.data.InitialAssessmentDbHelper;

public class CatagoryResultActivity extends AppCompatActivity {

    TextView categoryTextView;
    TextView scoreTextView;
    Button finishButton;
    int number;

    private InitialAssessmentDbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_result);

        categoryTextView = findViewById(R.id.txtCatagoryName);
        scoreTextView = findViewById(R.id.txtChildScore);
        finishButton = findViewById(R.id.btnFinish);

        final String score = String.valueOf(getIntent().getIntExtra("score", 0));
        final String categoryName = getIntent().getStringExtra("category");
        final Long id = getIntent().getLongExtra("id", -1);
        final int flag = getIntent().getIntExtra("date_flag", -1);
        final int assessmentNo = getIntent().getIntExtra("assessment_no", -1);

        if (categoryName.equals("Vocal Imitation"))
            number = 1;
        else if (categoryName.equals("Matching"))
            number = 2;
        else if (categoryName.equals("Labeling"))
            number = 3;
        else if (categoryName.equals("Receptive by FFC"))
            number = 4;
        else if (categoryName.equals("Conversational Skills"))
            number = 5;
        else
            number = 6;

        categoryTextView.setText(categoryName);
        scoreTextView.setText(score + " out of 5");

        db = new InitialAssessmentDbHelper(this);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateResult(score, number, id, flag, assessmentNo);

                finish();
            }
        });

    }
}
