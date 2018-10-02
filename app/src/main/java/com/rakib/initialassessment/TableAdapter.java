package com.rakib.initialassessment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rakib.initialassessment.model.Result;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private Context context;
    private List<String> resultList;

    public TableAdapter(Context context, List<String> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        if (resultList.get(position).equals("-1") || resultList.get(position).equals("null"))
            holder.mScoreTextView.setText("N/A");
        else
            holder.mScoreTextView.setText(resultList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {

        public TextView mScoreTextView;


        public TableViewHolder(View itemView) {
            super(itemView);
            mScoreTextView = itemView.findViewById(R.id.score_text);

        }
    }
}
