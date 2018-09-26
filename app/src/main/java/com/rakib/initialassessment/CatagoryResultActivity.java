package com.rakib.initialassessment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CatagoryResultActivity extends AppCompatActivity {

    TextView categoryTextView;
    TextView scoreTextView;
    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_result);

        categoryTextView = findViewById(R.id.txtCatagoryName);
        scoreTextView = findViewById(R.id.txtChildScore);
        finishButton = findViewById(R.id.btnFinish);

        String score = String.valueOf(getIntent().getIntExtra("score",0));
        String categoryName = getIntent().getStringExtra("category");

        categoryTextView.setText(categoryName);
        scoreTextView.setText(score + " out of 5");

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
