package com.example.firebaseapplication.ui.cleaner;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.adapter.ReviewAdapter;
import com.example.firebaseapplication.data.model.Review;
import com.example.firebaseapplication.utils.CleanerSessionManager;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDisplayActivity extends AppCompatActivity {

    private TextView cleanerNameText, cleanerEmailText;
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter adapter;
    private List<Review> reviewList = new ArrayList<>();
    private DatabaseReference reviewsRef;
    private String cleanerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_display);

        cleanerNameText = findViewById(R.id.cleanerNameText);
        cleanerEmailText = findViewById(R.id.cleanerEmailText);
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);

        CleanerSessionManager session = new CleanerSessionManager(this);
        cleanerId = session.getCleanerId();

        cleanerNameText.setText("Welcome, " + session.getName());
        cleanerEmailText.setText(session.getEmail());

        adapter = new ReviewAdapter(reviewList);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(adapter);

        reviewsRef = FirebaseDatabase.getInstance().getReference("reviews");

        loadCleanerReviews();
    }

    private void loadCleanerReviews() {
        reviewsRef.orderByChild("cleanerId").equalTo(cleanerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reviewList.clear();
                        for (DataSnapshot reviewSnap : snapshot.getChildren()) {
                            Review review = reviewSnap.getValue(Review.class);
                            if (review != null) {
                                reviewList.add(review);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ReviewDisplayActivity.this,
                                "Failed to load reviews", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

