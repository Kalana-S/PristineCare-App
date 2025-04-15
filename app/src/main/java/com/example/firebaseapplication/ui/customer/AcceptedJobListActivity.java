package com.example.firebaseapplication.ui.customer;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.adapter.AcceptedJobAdapter;
import com.example.firebaseapplication.data.model.BillingJob;
import com.example.firebaseapplication.utils.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AcceptedJobListActivity extends AppCompatActivity {

    private TextView customerNameText, customerEmailText;
    private RecyclerView acceptedJobRecyclerView;
    private AcceptedJobAdapter adapter;
    private List<BillingJob> acceptedJobs = new ArrayList<>();
    private DatabaseReference acceptedJobsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_job_list);

        customerNameText = findViewById(R.id.customerNameText);
        customerEmailText = findViewById(R.id.customerEmailText);
        acceptedJobRecyclerView = findViewById(R.id.acceptedJobRecyclerView);

        SessionManager session = new SessionManager(this);
        customerNameText.setText("Welcome, " + session.getName());
        customerEmailText.setText(session.getEmail());

        adapter = new AcceptedJobAdapter(acceptedJobs);
        acceptedJobRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        acceptedJobRecyclerView.setAdapter(adapter);

        acceptedJobsRef = FirebaseDatabase.getInstance().getReference("accepted_jobs");

        loadAcceptedJobs(session.getCustomerId());
    }

    private void loadAcceptedJobs(String customerId) {
        acceptedJobsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acceptedJobs.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    BillingJob job = snap.getValue(BillingJob.class);
                    if (job != null && customerId.equals(job.getCustomerId())) {
                        acceptedJobs.add(job);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AcceptedJobListActivity.this, "Failed to load jobs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
