package com.example.firebaseapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.Review;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.customerNameText.setText("Customer: " + review.getCustomerName());
        holder.ratingText.setText("Rating: " + review.getRating());
        holder.reviewText.setText("Review: " + review.getComment());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameText, ratingText, reviewText;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameText = itemView.findViewById(R.id.customerNameText);
            ratingText = itemView.findViewById(R.id.ratingText);
            reviewText = itemView.findViewById(R.id.reviewText);
        }
    }

}
