package com.example.firebaseapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.ui.customer.CustomerDashboardActivity;
import com.example.firebaseapplication.utils.SessionManager;
import com.example.firebaseapplication.viewmodel.CustomerAuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton, registerButton;
    private CustomerAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        emailInput = findViewById(R.id.loginEmailInput);
        passwordInput = findViewById(R.id.loginPasswordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        viewModel = new ViewModelProvider(this).get(CustomerAuthViewModel.class);

        viewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                fetchAndStoreCustomerSession();
            }
        });

        viewModel.getLoginError().observe(this, error -> {
            Toast.makeText(this, "Login Error: " + error, Toast.LENGTH_SHORT).show();
        });

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            viewModel.login(email, password);
        });

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(CustomerLoginActivity.this, CustomerRegisterActivity.class);
            startActivity(intent);
        });
    }

    private void fetchAndStoreCustomerSession() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("customers");

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);

                    SessionManager sessionManager = new SessionManager(CustomerLoginActivity.this);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    sessionManager.saveCustomerSession(uid, name, email, phone, address);

                    Toast.makeText(CustomerLoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(CustomerLoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CustomerLoginActivity.this, "Failed to load user info.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
