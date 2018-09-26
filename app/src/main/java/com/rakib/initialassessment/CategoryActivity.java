package com.rakib.initialassessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {

    CardView mVocalImitationCardView, mMatchingCardView, mLabelingCardView, mReceptiveByFFCCardView, mConversationalSkillCardView, mLettersNumbersCardView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ABBLS Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeView();

        mConversationalSkillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeView() {
        mVocalImitationCardView = findViewById(R.id.card_vocal_imitation);
        mLabelingCardView = findViewById(R.id.card_labeling);
        mMatchingCardView = findViewById(R.id.card_matching);
        mReceptiveByFFCCardView = findViewById(R.id.card_receptive_by_ffc);
        mConversationalSkillCardView = findViewById(R.id.card_conversational_skill);
        mLettersNumbersCardView = findViewById(R.id.card_letter_number);
    }

}
