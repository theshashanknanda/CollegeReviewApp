package com.project.ams.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ams.Models.ReviewModel;
import com.project.ams.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    public List<ReviewModel> reviewModelList;
    public Context context;

    public ReviewAdapter(List<ReviewModel> reviewModelList, Context context){
        this.reviewModelList = reviewModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        ReviewModel model = new ReviewModel();
        model = reviewModelList.get(position);

        holder.userNameText.setText(model.getUserName());
        holder.reviewText.setText(model.getReview());
        holder.ratingBar.setRating(Float.parseFloat(model.getRating()));
    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userNameText;
        public TextView reviewText;
        public RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameText = itemView.findViewById(R.id.reviewName_id);
            reviewText = itemView.findViewById(R.id.reviewText_id);
            ratingBar = itemView.findViewById(R.id.ratinBar_row_id);
        }
    }
}
