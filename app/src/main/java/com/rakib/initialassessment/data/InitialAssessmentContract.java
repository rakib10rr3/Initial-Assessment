package com.rakib.initialassessment.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.rakib.initialassessment.model.Student;

public class InitialAssessmentContract {
    private InitialAssessmentContract() {
    }

    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_STUDENT_NAME = "name";
        public static final String COLUMN_DOB = "age";
        public static final String COLUMN_GENDER = "gender";

    }

}
