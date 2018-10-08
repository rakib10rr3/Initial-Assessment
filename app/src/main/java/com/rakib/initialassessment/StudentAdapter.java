package com.rakib.initialassessment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakib.initialassessment.model.Student;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*;

import java.util.Date;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Date date1, date2;

    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date1 = format.parse(student.getDob());
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String date = sdf.format(new Date());
//        try {
//            date2 = format.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        date2 = Calendar.getInstance().getTime();

//        Log.d("aaa", student.getDob());
//        Log.d("aaa", date2.toString());

        int age = getDiffYears(date1, date2);

        holder.mNameTextView.setText(student.getName());
        holder.mAgeTextView.setText(String.valueOf(age) + " years old");
        holder.mGenderTextView.setText(student.getGender());
        holder.mDotTextView.setText(Html.fromHtml("&#8226;"));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView;
        public TextView mDotTextView;
        public TextView mAgeTextView;
        public TextView mGenderTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name);
            mAgeTextView = itemView.findViewById(R.id.age);
            mGenderTextView = itemView.findViewById(R.id.gender);
            mDotTextView = itemView.findViewById(R.id.dot);
        }
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }

        if (diff == 0)
        {
           diff = b.get(MONTH) - a.get(MONTH);
            if (a.get(MONTH) > b.get(MONTH) ||
                    (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
                diff--;
            }

        }

        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
