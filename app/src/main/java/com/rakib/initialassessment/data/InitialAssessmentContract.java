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

    public static class QuestionEntry implements BaseColumns{
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_QUESTION_NAME = "ques";
        public static final String COLUMN_OPTION_A = "opta";
        public static final String COLUMN_OPTION_B = "optb";
        public static final String COLUMN_OPTION_C = "optc";
        public static final String COLUMN_ANSWER = "ans";
        public static final String COLUMN_CATEGORY = "cat";


    }

    public static class ResultEntry implements BaseColumns{
        public static final String TABLE_NAME="result";
        public static final String COLUMN_VOCAL_IMITATION="vocal_imitation";
        public static final String COLUMN_MATCHING_TO_SAMPLE="matching";
        public static final String COLUMN_LABELING="labeling";
        public static final String COLUMN_RECEPTIVE_BY_FFC="receptive_by_ffc";
        public static final String COLUMN_CONVERSATIONAL_SKILLS="conversational";
        public static final String COLUMN_LETTERS_NUMBERS="letters_numbers";
        public static final String COLUMN_STUDENT_ID="student_id";
    }

}
