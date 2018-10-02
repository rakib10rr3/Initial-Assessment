package com.rakib.initialassessment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.rakib.initialassessment.data.InitialAssessmentDbHelper;
import com.rakib.initialassessment.model.Result;
import com.rakib.initialassessment.util.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {


    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> list=new ArrayList<>();

    long id;
    String name;

    private InitialAssessmentDbHelper db;
    List<Result> results;
    List<String> scores = new ArrayList<>();

    TableAdapter tableAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        id = getIntent().getLongExtra("id",-1);
        name = getIntent().getStringExtra("name");


        getSupportActionBar().setTitle("Scores of " + name);

        db = new InitialAssessmentDbHelper(this);
        results = db.getAllResults(id);
        Log.d("results: ", String.valueOf(results.size()));

        scores.add("Assessment Date");
        scores.add("Vocal Imitation");
        scores.add("Matching with Sample");
        scores.add("Labeling");
        scores.add("Receptive by FFC");
        scores.add("Conversational Skills");
        scores.add("Letters and Numbers");
        scores.add("Assessment No");


        for (int i=0; i<results.size();i++)
        {
            scores.add(String.valueOf(results.get(i).getAssessmentDate()));
            scores.add(String.valueOf(results.get(i).getVocalImitation()));
            scores.add(String.valueOf(results.get(i).getMatching()));
            scores.add(String.valueOf(results.get(i).getLabeling()));
            scores.add(String.valueOf(results.get(i).getReceptiveByFFC()));
            scores.add(String.valueOf(results.get(i).getConversationalSkills()));
            scores.add(String.valueOf(results.get(i).getLettersNumbers()));
            scores.add(String.valueOf(results.get(i).getAssessmentNo()));
        }





        Log.d("yyy",String.valueOf(scores.size()));


        recyclerView = findViewById(R.id.recycler_table);
        //recyclerView.setHasFixedSize(true);
        tableAdapter = new TableAdapter(this, scores);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),8,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(tableAdapter);


    }

}
