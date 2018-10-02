package com.rakib.initialassessment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.initialassessment.data.InitialAssessmentDbHelper;
import com.rakib.initialassessment.model.Result;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    CardView mVocalImitationCardView, mMatchingCardView, mLabelingCardView, mReceptiveByFFCCardView, mConversationalSkillCardView, mLettersNumbersCardView;

    TextView mVocalImitationScoreTextView, mMatchingScoreTextView, mLabelingScoreTextView, mReceptiveByFFCTextView, mConversatinalSkillsTextView, mLettersNumbersTextView;

    Intent intent;

    String name;
    long id;
    int assessmemntNo;

    List<Result> results;

    private InitialAssessmentDbHelper db;

    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = getIntent().getStringExtra("name");
        id = getIntent().getLongExtra("id", -1);
        Log.d("ex_id", String.valueOf(id));

        getSupportActionBar().setTitle("ABLLS Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new InitialAssessmentDbHelper(this);
        results = db.getAllResults(id);

        assessmemntNo = results.size();

        initializeView();

        if (results.get(results.size()-1).getVocalImitation() == -1) {
            mVocalImitationScoreTextView.setText("NOT ASSESSED");
            mVocalImitationScoreTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mVocalImitationScoreTextView.setText(results.get(results.size()-1).getVocalImitation() + " OUT OF 5");
            mVocalImitationScoreTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }

        if (results.get(results.size()-1).getMatching() == -1) {
            mMatchingScoreTextView.setText("NOT ASSESSED");
            mMatchingScoreTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mMatchingScoreTextView.setText(results.get(results.size()-1).getMatching() + " OUT OF 5");
            mMatchingScoreTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }

        if (results.get(results.size()-1).getLabeling() == -1) {
            mLabelingScoreTextView.setText("NOT ASSESSED");
            mLabelingScoreTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mLabelingScoreTextView.setText(results.get(results.size()-1).getLabeling() + " OUT OF 5");
            mLabelingScoreTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }

        if (results.get(results.size()-1).getReceptiveByFFC() == -1) {
            mReceptiveByFFCTextView.setText("NOT ASSESSED");
            mReceptiveByFFCTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mReceptiveByFFCTextView.setText(results.get(results.size()-1).getReceptiveByFFC() + " OUT OF 5");
            mReceptiveByFFCTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }

        if (results.get(results.size()-1).getConversationalSkills() == -1) {
            mConversatinalSkillsTextView.setText("NOT ASSESSED");
            mConversatinalSkillsTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mConversatinalSkillsTextView.setText(results.get(results.size()-1).getConversationalSkills() + " OUT OF 5");
            mConversatinalSkillsTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }

        if (results.get(results.size()-1).getLettersNumbers() == -1) {
            mLettersNumbersTextView.setText("NOT ASSESSED");
            mLettersNumbersTextView.setTextColor(Color.parseColor("#ffff4444"));
        } else {
            mLettersNumbersTextView.setText(results.get(results.size()-1).getLettersNumbers() + " OUT OF 5");
            mLettersNumbersTextView.setTextColor(Color.parseColor("#ff99cc00"));
        }


        if (results.get(results.size()-1).getVocalImitation() == -1 && results.get(results.size()-1).getMatching() == -1 && results.get(results.size()-1).getLabeling() == -1 && results.get(results.size()-1).getReceptiveByFFC() == -1 && results.get(results.size()-1).getConversationalSkills() == -1 && results.get(results.size()-1).getLettersNumbers() == -1)

        {
            flag = 0;
        }

        if (results.get(results.size()-1).getVocalImitation() > -1 && results.get(results.size()-1).getMatching() > -1 && results.get(results.size()-1).getLabeling() > -1 && results.get(results.size()-1).getReceptiveByFFC() > -1 && results.get(results.size()-1).getConversationalSkills() > -1 && results.get(results.size()-1).getLettersNumbers() > -1)

        {
            flag = 1;
            long resultId = db.insertResult(new Result(-1,"null",-1,-1,-1,-1,-1,-1,id,results.size()+1));
            assessmemntNo = results.size()+1;
        }


        mConversationalSkillCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), QuizActivity.class);

                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("cat", "Conversational Skills");
                intent.putExtra("has_image", "no");
                intent.putExtra("date_flag",flag);
                intent.putExtra("assessment_no",assessmemntNo);

                //Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        mLabelingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("cat", "Labeling");
                intent.putExtra("has_image", "yes");
                intent.putExtra("date_flag",flag);
                intent.putExtra("assessment_no",assessmemntNo);
                //Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        mMatchingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("cat", "Matching");
                intent.putExtra("has_image", "yes");
                intent.putExtra("date_flag",flag);
                intent.putExtra("assessment_no",assessmemntNo);
                //Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        mLettersNumbersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("cat", "Letters and Numbers");
                intent.putExtra("has_image", "yes");
                intent.putExtra("date_flag",flag);
                intent.putExtra("assessment_no",assessmemntNo);
                //Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });

        mReceptiveByFFCCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("cat", "Receptive by FFC");
                intent.putExtra("has_image", "no");
                intent.putExtra("date_flag",flag);
                intent.putExtra("assessment_no",assessmemntNo);
                //Toast.makeText(CategoryActivity.this, "conversation", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_table:
                intent = new Intent(getApplicationContext(), TableActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                Log.d("table", String.valueOf(id));
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void initializeView() {
        mVocalImitationCardView = findViewById(R.id.card_vocal_imitation);
        mLabelingCardView = findViewById(R.id.card_labeling);
        mMatchingCardView = findViewById(R.id.card_matching);
        mReceptiveByFFCCardView = findViewById(R.id.card_receptive_by_ffc);
        mConversationalSkillCardView = findViewById(R.id.card_conversational_skill);
        mLettersNumbersCardView = findViewById(R.id.card_letter_number);

        mVocalImitationScoreTextView = findViewById(R.id.text_vocal_imitation_score);
        mMatchingScoreTextView = findViewById(R.id.text_matching_score);
        mLabelingScoreTextView = findViewById(R.id.text_labeling_score);
        mReceptiveByFFCTextView = findViewById(R.id.text_receptive_by_ffc_score);
        mConversatinalSkillsTextView = findViewById(R.id.text_conversational_skill_score);
        mLettersNumbersTextView = findViewById(R.id.text_letters_numbers_score);
    }

}
