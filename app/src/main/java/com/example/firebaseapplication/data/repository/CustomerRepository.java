package com.example.firebaseapplication.data.repository;

import com.example.firebaseapplication.data.model.Customer;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class CustomerRepository {

    private final FirebaseAuth mAuth;
    private final DatabaseReference customerRef;

    public CustomerRepository() {
        mAuth = FirebaseAuth.getInstance();
        customerRef = FirebaseDatabase.getInstance().getReference("customers");
    }

    public interface RegisterCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public void registerCustomer(String name, String email, String phone, String address, String password, RegisterCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        Customer customer = new Customer(userId, name, email, phone, address);
                        customerRef.child(userId).setValue(customer)
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

    public void loginCustomer(String email, String password, RegisterCallback callback) {
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
