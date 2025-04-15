package com.example.firebaseapplication.data.repository;

import androidx.annotation.NonNull;
import com.example.firebaseapplication.data.model.Review;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    private final DatabaseReference reviewRef;

    public ReviewRepository() {
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews");
    }

    public void addReview(Review review, OnReviewAddedListener listener) {
        String reviewId = reviewRef.push().getKey();
        review.setReviewId(reviewId);

        if (reviewId != null) {
            reviewRef.child(reviewId)
                    .setValue(review)
                    .addOnSuccessListener(unused -> listener.onSuccess())
                    .addOnFailureListener(listener::onFailure);
        } else {
            listener.onFailure(new Exception("Failed to generate review ID"));
        }
    }

    public void getReviewsForCleaner(String cleanerId, OnReviewsFetchedListener listener) {
        reviewRef.orderByChild("cleanerId").equalTo(cleanerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Review> reviews = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Review review = data.getValue(Review.class);
                            if (review != null) {
                                reviews.add(review);
                            }
                        }
                        listener.onSuccess(reviews);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFailure(error.toException());
                    }
                });
    }

    public interface OnReviewAddedListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    public interface OnReviewsFetchedListener {
        void onSuccess(List<Review> reviews);
        void onFailure(Exception e);
    }

}
