package com.project.ams.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ams.activities.CollegeDetailActivity;
import com.project.ams.Models.CollegeModel;
import com.project.ams.R;

import java.util.List;

public class CollegeListAdapter extends RecyclerView.Adapter<CollegeListAdapter.ViewHolder> {
    public List<CollegeModel> collegeModelList;
    public Context context;

    public CollegeListAdapter(List<CollegeModel> collegeModelList, Context context){
        this.collegeModelList = collegeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CollegeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.collegelist_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegeListAdapter.ViewHolder holder, int position) {
        CollegeModel model = collegeModelList.get(position);

        holder.collegeName.setText(model.getName());
        holder.location.setText(model.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start new activity with current college data

                Intent intent = new Intent(context.getApplicationContext(), CollegeDetailActivity.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("location", model.getLocation());
                intent.putExtra("minimumMarks", model.getMinimumMarks());
                intent.putExtra("email", model.getCollegeEmail());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collegeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView collegeName, location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            collegeName = itemView.findViewById(R.id.collegeNameTextView_id);
            location = itemView.findViewById(R.id.collegeLocationTextView_ids);
        }
    }
}
