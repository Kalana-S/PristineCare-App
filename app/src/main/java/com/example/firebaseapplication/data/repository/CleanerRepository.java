package com.example.firebaseapplication.data.repository;

import com.example.firebaseapplication.data.model.Cleaner;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class CleanerRepository {

    private final FirebaseAuth mAuth;
    private final DatabaseReference cleanerRef;

    public CleanerRepository() {
        mAuth = FirebaseAuth.getInstance();
        cleanerRef = FirebaseDatabase.getInstance().getReference("cleaners");
    }

    public interface RegisterCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public void registerCleaner(String name, String email, String phone, String password, CleanerRepository.RegisterCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        Cleaner cleaner = new Cleaner(userId, name, email, phone);
                        cleanerRef.child(userId).setValue(cleaner)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        callback.onSuccess();
                                    } else {
                                        callback.onFailure(task1.getException().getMessage());
                                    }
                                });
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void loginCleaner(String email, String password, CleanerRepository.RegisterCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });

    }

}
