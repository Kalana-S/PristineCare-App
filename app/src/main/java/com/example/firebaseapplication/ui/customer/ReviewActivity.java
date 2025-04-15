package com.example.firebaseapplication.ui.customer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.Review;
import com.example.firebaseapplication.data.repository.ReviewRepository;
import com.example.firebaseapplication.utils.SessionManager;

public class ReviewActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private Button submitButton;
    private TextView cleanerNameText;

    private String jobId, cleanerId, cleanerName, customerId, customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = findViewById(R.id.ratingBar);
        commentEditText = findViewById(R.id.commentEditText);
        submitButton = findViewById(R.id.submitReviewButton);
        cleanerNameText = findViewById(R.id.cleanerNameTextView);

        Intent intent = getIntent();
        jobId = intent.getStringExtra("jobId");
        cleanerId = intent.getStringExtra("cleanerId");
        cleanerName = intent.getStringExtra("cleanerName");

        SessionManager session = new SessionManager(this);
        customerId = session.getCustomerId();
        customerName = session.getName();

        cleanerNameText.setText("Review for: " + cleanerName);

        submitButton.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        float rating = ratingBar.getRating();
        String comment = commentEditText.getText().toString().trim();
        long timestamp = System.currentTimeMillis();

        Review review = new Review();
        review.setJobId(jobId);
        review.setCleanerId(cleanerId);
        review.setCleanerName(cleanerName);
        review.setCustomerId(customerId);
        review.setCustomerName(customerName);
        review.setRating(rating);
        review.setComment(comment);
        review.setTimestamp(timestamp);

        ReviewRepository reviewRepo = new ReviewRepository();
        reviewRepo.addReview(review, new ReviewRepository.OnReviewAddedListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ReviewActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ReviewActivity.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
            }
        });
    }

}