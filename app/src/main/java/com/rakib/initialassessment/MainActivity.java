package com.rakib.initialassessment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.initialassessment.data.InitialAssessmentDbHelper;
import com.rakib.initialassessment.model.Student;
import com.rakib.initialassessment.util.MyDividerItemDecoration;
import com.rakib.initialassessment.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private StudentAdapter mStudentAdapter;
    private List<Student> studentList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noStudentTextView;
    private InitialAssessmentDbHelper db;

    String name;
    String dob;
    String gender;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.studentRecyclerView);
        noStudentTextView = findViewById(R.id.empty_student_view);

        db = new InitialAssessmentDbHelper(this);

        studentList.addAll(db.getAllStudents());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);

//                Intent testIntent = new Intent(getApplicationContext(), CatagoryResultActivity.class);
//                startActivity(testIntent);
            }
        });

        mStudentAdapter = new StudentAdapter(this, studentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mStudentAdapter);

        toggleEmptyStudents();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Toast.makeText(MainActivity.this, studentList.get(position).getName(), Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), CategoryActivity.class);
                intent.putExtra("name", studentList.get(position).getName());
                intent.putExtra("id",studentList.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //showActionsDialog(position);
            }
        }));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showNoteDialog(final boolean shouldUpdate, final Student student, final int position) {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        final View view = layoutInflaterAndroid.inflate(R.layout.create_student, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText mNameEditText = view.findViewById(R.id.name);
        final EditText mDobEditText = view.findViewById(R.id.dob);
        final RadioGroup mRadioGroup = view.findViewById(R.id.radio_group);

        //final int selectedId = mRadioGroup.getCheckedRadioButtonId();


        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    mDobEditText.setText(current);
                    mDobEditText.setSelection(sel < current.length() ? sel : current.length());
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        mDobEditText.addTextChangedListener(tw);


        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_student_title) : getString(R.string.lbl_edit_student_title));

        if (shouldUpdate && student != null) {
            mNameEditText.setText(student.getName());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(mNameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(mDobEditText.getText().toString()) || mDobEditText.getText().toString().contains("D") || mDobEditText.getText().toString().contains("M") || mDobEditText.getText().toString().contains("Y")) {
                    Toast.makeText(MainActivity.this, "Enter a valid date!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    final int selectedId = mRadioGroup.getCheckedRadioButtonId();
                    final RadioButton mRadioButton = view.findViewById(selectedId);
                    gender = mRadioButton.getText().toString();
                    Log.d("xxx", String.valueOf(selectedId));
                    //Toast.makeText(MainActivity.this, mNameEditText.getText().toString() + "\n" + mDobEditText.getText().toString() + "\n" +gender, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }

                // check if user updating note
                if (shouldUpdate && student != null) {
                    // update note by it's id
                    //updateNote(inputNote.getText().toString(), position);
                } else {
                    // create new note
                    name = mNameEditText.getText().toString();
                    dob = mDobEditText.getText().toString();

//                    Log.d("aaa", dob);

                    db.insertStudent(new Student(-1,name,dob,gender));
                    studentList.clear();
                    studentList.addAll(db.getAllStudents());
                    mStudentAdapter.notifyDataSetChanged();
                    toggleEmptyStudents();
                }
            }
        });
    }

    private void toggleEmptyStudents() {
        // you can check notesList.size() > 0

        if (db.getStudentsCount() > 0) {
            noStudentTextView.setVisibility(View.GONE);
        } else {
            noStudentTextView.setVisibility(View.VISIBLE);
        }
    }


}
