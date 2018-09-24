package com.rakib.initialassessment.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rakib.initialassessment.data.InitialAssessmentContract.StudentEntry;
import com.rakib.initialassessment.model.Student;

import java.util.ArrayList;
import java.util.List;

public class InitialAssessmentDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "initial_assessment.db";

    public InitialAssessmentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE =
                "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" +
                        StudentEntry._ID + " INTEGER PRIMARY KEY," +
                        StudentEntry.COLUMN_STUDENT_NAME + " TEXT," +
                        StudentEntry.COLUMN_DOB + " TEXT NOT NULL," +
                        StudentEntry.COLUMN_GENDER + " TEXT NOT NULL)";

        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + StudentEntry.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertStudent(Student student) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(StudentEntry.COLUMN_STUDENT_NAME, student.getName());
        values.put(StudentEntry.COLUMN_DOB, student.getDob());
        values.put(StudentEntry.COLUMN_GENDER, student.getGender());

        // insert row
        long id = db.insert(StudentEntry.TABLE_NAME, null, values);

        // close db connection
        db.close();

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

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex(StudentEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_NAME));
                String dob = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_DOB));
                String gender = cursor.getString(cursor.getColumnIndex(StudentEntry.COLUMN_GENDER));

                students.add(new Student(id,name,dob,gender));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return students;
    }

    public int getNotesCount() {
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
}
