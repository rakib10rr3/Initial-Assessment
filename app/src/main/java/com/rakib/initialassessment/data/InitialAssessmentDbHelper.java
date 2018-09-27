package com.rakib.initialassessment.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.rakib.initialassessment.data.InitialAssessmentContract.StudentEntry;
import com.rakib.initialassessment.data.InitialAssessmentContract.QuestionEntry;
import com.rakib.initialassessment.data.InitialAssessmentContract.ResultEntry;

import com.rakib.initialassessment.model.Question;
import com.rakib.initialassessment.model.Result;
import com.rakib.initialassessment.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitialAssessmentDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 11;
    private static final String DATABASE_NAME = "initial_assessment.db";

    private SQLiteDatabase dBase;

    public InitialAssessmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dBase = db;
        String CREATE_STUDENT_TABLE =
                "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" +
                        StudentEntry._ID + " INTEGER PRIMARY KEY," +
                        StudentEntry.COLUMN_STUDENT_NAME + " TEXT," +
                        StudentEntry.COLUMN_DOB + " TEXT NOT NULL," +
                        StudentEntry.COLUMN_GENDER + " TEXT NOT NULL)";

        String CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                QuestionEntry._ID + " INTEGER PRIMARY KEY," +
                QuestionEntry.COLUMN_QUESTION_NAME + " TEXT," +
                QuestionEntry.COLUMN_OPTION_A + " TEXT ," +
                QuestionEntry.COLUMN_OPTION_B + " TEXT ," +
                QuestionEntry.COLUMN_OPTION_C + " TEXT ," +
                QuestionEntry.COLUMN_ANSWER + " TEXT ," +
                QuestionEntry.COLUMN_CATEGORY + " TEXT)";

        String CREATE_RESULT_TABLE = "CREATE TABLE " + ResultEntry.TABLE_NAME + " (" +
                ResultEntry._ID + " INTEGER PRIMARY KEY," +
                ResultEntry.COLUMN_VOCAL_IMITATION + " INTEGER," +
                ResultEntry.COLUMN_MATCHING_TO_SAMPLE + " INTEGER," +
                ResultEntry.COLUMN_LABELING + " INTEGER," +
                ResultEntry.COLUMN_RECEPTIVE_BY_FFC + " INTEGER," +
                ResultEntry.COLUMN_CONVERSATIONAL_SKILLS + " INTEGER," +
                ResultEntry.COLUMN_LETTERS_NUMBERS + " INTEGER," +
                ResultEntry.COLUMN_STUDENT_ID + " INTEGER, " +
                "FOREIGN KEY (" + ResultEntry.COLUMN_STUDENT_ID + ") REFERENCES " + StudentEntry.TABLE_NAME + "(" + StudentEntry._ID + ") ON UPDATE CASCADE ON DELETE CASCADE )";

        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_RESULT_TABLE);

        addConversationalSkillsQuestions();
        addLabelingQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + StudentEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ResultEntry.TABLE_NAME);


        // Create tables again
        onCreate(db);
    }

    public long insertStudent(Student student) {
        // get writable database as we want to write data
        dBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(StudentEntry.COLUMN_STUDENT_NAME, student.getName());
        values.put(StudentEntry.COLUMN_DOB, student.getDob());
        values.put(StudentEntry.COLUMN_GENDER, student.getGender());

        // insert row
        long id = dBase.insert(StudentEntry.TABLE_NAME, null, values);

        // close db connection
        dBase.close();

        // return newly inserted row id
        return id;
    }

//    public Student getStudent(long id) {
//        // get readable database as we are not inserting anything
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(StudentEntry.TABLE_NAME,
//                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP},
//                Note.COLUMN_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // prepare note object
//        Note note = new Note(
//                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
//                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
//                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
//
//        // close the db connection
//        cursor.close();
//
//        return note;
//    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + StudentEntry.TABLE_NAME;

        dBase = this.getWritableDatabase();
        Cursor cursor = dBase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex(StudentEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_NAME));
                String dob = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_DOB));
                String gender = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_GENDER));

                students.add(new Student(id, name, dob, gender));
            } while (cursor.moveToNext());
        }

        // close db connection
        dBase.close();

        // return notes list
        return students;
    }

    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + StudentEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

//    public int updateNote(Note note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Note.COLUMN_NOTE, note.getNote());
//
//        // updating row
//        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
//                new String[]{String.valueOf(note.getId())});
//    }
//
//    public void deleteNote(Note note) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
//                new String[]{String.valueOf(note.getId())});
//        db.close();
//    }

    private void addConversationalSkillsQuestions() {
        Question q1 = new Question(-1, "_____ is my country", "USA", "Bangladesh", "India", "Bangladesh", "Conversational Skills");
        Question q2 = new Question(-1, "_____ little star", "Twinkle twinkle", "Bangladesh", "India", "Twinkle twinkle", "Conversational Skills");
        Question q3 = new Question(-1, "_____ sat on a wall", "Shafee", "Shohag", "Humpty dumpty", "Humpty dumpty", "Conversational Skills");
        Question q4 = new Question(-1, "Mary had a little _____", "Cat", "Lamb", "Dog", "Lamb", "Conversational Skills");
        Question q5 = new Question(-1, "Baa baa black sheep, have you any ____?", "wool", "toy", "car", "wool", "Conversational Skills");

        this.addQuestion(q1);
        this.addQuestion(q2);
        this.addQuestion(q3);
        this.addQuestion(q4);
        this.addQuestion(q5);
    }

    private void addLabelingQuestions()
    {
        Question q1 = new Question(-1, "ic_cow", "Cat", "Snake", "Cow", "Cow", "Labeling");
        Question q2 = new Question(-1, "ic_dog", "Horse", "Dog", "Goat", "Dog", "Labeling");
        Question q3 = new Question(-1, "ic_soccer", "Football", "Cricket Ball", "Egg", "Football", "Labeling");
        Question q4 = new Question(-1, "ic_automobile", "Car", "Bus", "Truck", "Car", "Labeling");
        Question q5 = new Question(-1, "ic_chopper", "Aeroplane", "Ship", "Helicopter", "Helicopter", "Labeling");
        this.addQuestion(q1);
        this.addQuestion(q2);
        this.addQuestion(q3);
        this.addQuestion(q4);
        this.addQuestion(q5);
    }

    public void addQuestion(Question question) {

        ContentValues values = new ContentValues();
        values.put(QuestionEntry.COLUMN_QUESTION_NAME, question.getQuestion());
        values.put(QuestionEntry.COLUMN_OPTION_A, question.getOptionA());
        values.put(QuestionEntry.COLUMN_OPTION_B, question.getOptionB());
        values.put(QuestionEntry.COLUMN_OPTION_C, question.getOptionC());
        values.put(QuestionEntry.COLUMN_ANSWER, question.getCorrect());
        values.put(QuestionEntry.COLUMN_CATEGORY, question.getCategory());
        dBase.insert(QuestionEntry.TABLE_NAME, null, values);
    }

    public List<Question> getAllQuestions(String category) {
        List<Question> questionList = new ArrayList<>();
       // String selectQuery = "SELECT * FROM " + QuestionEntry.TABLE_NAME + " WHERE " + QuestionEntry.COLUMN_CATEGORY + "=" + category + ";";
        dBase = this.getReadableDatabase();

        String[] columns ={QuestionEntry._ID, QuestionEntry.COLUMN_QUESTION_NAME,QuestionEntry.COLUMN_OPTION_A,QuestionEntry.COLUMN_OPTION_B,QuestionEntry.COLUMN_OPTION_C,QuestionEntry.COLUMN_ANSWER,QuestionEntry.COLUMN_CATEGORY};
       // Cursor findEntry = db.query("sku_table", columns, "owner=?", new String[] { owner }, null, null, null);


        Cursor cursor = dBase.query(QuestionEntry.TABLE_NAME, null, "cat=?",new String[] {category},null,null,null);

        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQuestion(cursor.getString(1));
                quest.setOptionA(cursor.getString(2));
                quest.setOptionB(cursor.getString(3));
                quest.setOptionC(cursor.getString(4));
                quest.setCorrect(cursor.getString(5));
                quest.setCategory(cursor.getString(6));

                questionList.add(quest);
            } while (cursor.moveToNext());
        }
        dBase.close();
        return questionList;
    }

    public int questionsCount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + QuestionEntry.TABLE_NAME ;
        dBase = this.getWritableDatabase();
        Cursor cursor = dBase.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }

    public long insertResult(Result result)
    {
        dBase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ResultEntry.COLUMN_VOCAL_IMITATION, result.getVocalImitation());
        values.put(ResultEntry.COLUMN_MATCHING_TO_SAMPLE, result.getMatching());
        values.put(ResultEntry.COLUMN_LABELING, result.getLabeling());
        values.put(ResultEntry.COLUMN_RECEPTIVE_BY_FFC, result.getReceptiveByFFC());
        values.put(ResultEntry.COLUMN_CONVERSATIONAL_SKILLS, result.getConversationalSkills());
        values.put(ResultEntry.COLUMN_LETTERS_NUMBERS, result.getLettersNumbers());
        values.put(ResultEntry.COLUMN_STUDENT_ID, result.getStudentID());


        // insert row
        long id = dBase.insert(ResultEntry.TABLE_NAME, null, values);

        // close db connection
        dBase.close();

        // return newly inserted row id
        return id;
    }

    public List<Result> getAllResults(long studentID)
    {
        List<Result> resultList = new ArrayList<>();

        dBase = this.getReadableDatabase();

        Cursor cursor = dBase.query(ResultEntry.TABLE_NAME, null, "student_id=?",new String[] {String.valueOf(studentID)},null,null,null);

        if (cursor.moveToFirst()) {
            do {

                Result result = new Result();
                result.setId(cursor.getLong(0));
                result.setVocalImitation(cursor.getInt(1));
                result.setMatching(cursor.getInt(2));
                result.setLabeling(cursor.getInt(3));
                result.setReceptiveByFFC(cursor.getInt(4));
                result.setConversationalSkills(cursor.getInt(5));
                result.setLettersNumbers(cursor.getInt(6));
                result.setStudentID(cursor.getLong(7));

                resultList.add(result);
            } while (cursor.moveToNext());
        }
        dBase.close();
        return resultList;
    }

    public long updateResult(String score, int categoryNumber, long studentID)
    {
        long rowCount = 0;
        dBase = this.getWritableDatabase();

        Log.d("ccc",String.valueOf(score) + String.valueOf(categoryNumber) + String.valueOf(studentID));

        ContentValues contentValues = new ContentValues();

        if (categoryNumber == 3)
            contentValues.put(ResultEntry.COLUMN_LABELING,Integer.parseInt(score));
        else if (categoryNumber == 5)
            contentValues.put(ResultEntry.COLUMN_CONVERSATIONAL_SKILLS,Integer.parseInt(score));
        try {
            rowCount = dBase.update(ResultEntry.TABLE_NAME, contentValues,
                    ResultEntry.COLUMN_STUDENT_ID + " = ? ",
                    new String[] {String.valueOf(studentID)});
        } catch (SQLiteException e){
//            Log.d("Exception: " + e.getMessage());
//            Toast.makeText(, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            dBase.close();
        }

        return rowCount;
    }
}
