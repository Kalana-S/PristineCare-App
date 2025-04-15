package com.example.firebaseapplication.ui.cleaner;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.utils.CleanerSessionManager;
import android.widget.TextView;
import android.widget.Button;

public class CleanerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_dashboard);

        TextView nameText = findViewById(R.id.dashboardName);
        TextView emailText = findViewById(R.id.dashboardEmail);
        Button btnBillingJob = findViewById(R.id.btnBillingJob);
        Button btnViewReview = findViewById(R.id.btnViewReview);

        CleanerSessionManager session = new CleanerSessionManager(this);

        nameText.setText(session.getName());
        emailText.setText(session.getEmail());

        btnBillingJob.setOnClickListener(view -> {

            startActivity(new Intent(CleanerDashboardActivity.this, BillingJobListActivity.class));

        });

        btnViewReview.setOnClickListener(view -> {

            startActivity(new Intent(CleanerDashboardActivity.this, ReviewDisplayActivity.class));

        });

    }

}