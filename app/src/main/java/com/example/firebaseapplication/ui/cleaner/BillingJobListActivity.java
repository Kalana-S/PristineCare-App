package com.example.firebaseapplication.ui.cleaner;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.adapter.BillingJobAdapter;
import com.example.firebaseapplication.data.model.BillingJob;
import com.example.firebaseapplication.utils.CleanerSessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BillingJobListActivity extends AppCompatActivity {

    private TextView cleanerNameText, cleanerEmailText;
    private RecyclerView billingJobRecyclerView;
    private BillingJobAdapter adapter;
    private List<BillingJob> billingJobList = new ArrayList<>();
    private DatabaseReference billingJobRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_job_list);

        cleanerNameText = findViewById(R.id.cleanerNameText);
        cleanerEmailText = findViewById(R.id.cleanerEmailText);
        billingJobRecyclerView = findViewById(R.id.billingJobRecyclerView);

        CleanerSessionManager session = new CleanerSessionManager(this);
        cleanerNameText.setText("Welcome, " + session.getName());
        cleanerEmailText.setText(session.getEmail());

        adapter = new BillingJobAdapter(billingJobList, this::onProceedClicked);
        billingJobRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        billingJobRecyclerView.setAdapter(adapter);

        billingJobRef = FirebaseDatabase.getInstance().getReference("cleaning_jobs");

        loadBillingJobs();
    }

    private void loadBillingJobs() {
        billingJobRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                billingJobList.clear();
                for (DataSnapshot billingJobSnap : snapshot.getChildren()) {
                    BillingJob billingJob = billingJobSnap.getValue(BillingJob.class);
                    if (billingJob != null) {
                        billingJobList.add(billingJob);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BillingJobListActivity.this,
                        "Error loading jobs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onProceedClicked(BillingJob billingJob) {
        CleanerSessionManager session = new CleanerSessionManager(this);

        DatabaseReference acceptedJobsRef = FirebaseDatabase.getInstance().getReference("accepted_jobs");
        String jobId = acceptedJobsRef.push().getKey();

        Map<String, Object> jobMap = new HashMap<>();
        jobMap.put("jobId", jobId);
        jobMap.put("customerId", billingJob.getCustomerId());
        jobMap.put("customerName", billingJob.getCustomerName());
        jobMap.put("customerEmail", billingJob.getCustomerEmail());
        jobMap.put("houseId", billingJob.getHouseId());
        jobMap.put("numberOfRooms", billingJob.getNumberOfRooms());
        jobMap.put("numberOfBathrooms", billingJob.getNumberOfBathrooms());
        jobMap.put("flooringType", billingJob.getFlooringType());
        jobMap.put("cleaningType", billingJob.getCleaningType());
        jobMap.put("photoBase64", billingJob.getPhotoBase64());
        jobMap.put("totalPrice", billingJob.getTotalPrice());
        jobMap.put("cleanerId", session.getCleanerId());
        jobMap.put("cleanerName", session.getName());
        jobMap.put("cleanerEmail", session.getEmail());
        jobMap.put("status", billingJob.getStatus());

        if (jobId != null) {
            acceptedJobsRef.child(jobId).setValue(jobMap)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Job Accepted & Saved", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to save job", Toast.LENGTH_SHORT).show()
                    );
        }
    }

}

