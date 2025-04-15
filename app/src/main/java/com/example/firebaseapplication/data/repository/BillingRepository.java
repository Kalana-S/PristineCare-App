package com.example.firebaseapplication.data.repository;

import com.example.firebaseapplication.data.model.BillingJob;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BillingRepository {

    private final DatabaseReference billingRef;

    public BillingRepository() {
        billingRef = FirebaseDatabase.getInstance().getReference("cleaning_jobs");
    }

    public void submitJob(BillingJob job, OnJobSubmitListener listener) {
        billingRef.child(job.getJobId()).setValue(job)
                .addOnSuccessListener(unused -> listener.onSuccess())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public interface OnJobSubmitListener {
        void onSuccess();
        void onFailure(String error);
    }

}
