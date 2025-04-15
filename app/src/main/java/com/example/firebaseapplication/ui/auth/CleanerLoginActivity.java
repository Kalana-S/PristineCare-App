package com.example.firebaseapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.ui.cleaner.CleanerDashboardActivity;
import com.example.firebaseapplication.utils.CleanerSessionManager;
import com.example.firebaseapplication.viewmodel.CleanerAuthViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class CleanerLoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton, registerButton;
    private CleanerAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_login);

        emailInput = findViewById(R.id.loginEmailInput);
        passwordInput = findViewById(R.id.loginPasswordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        viewModel = new ViewModelProvider(this).get(CleanerAuthViewModel.class);

        viewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                fetchAndStoreCleanerSession();
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
            Intent intent = new Intent(CleanerLoginActivity.this, CleanerRegisterActivity.class);
            startActivity(intent);
        });
    }

    private void fetchAndStoreCleanerSession() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("cleaners");

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);

                    CleanerSessionManager cleanerSessionManager = new CleanerSessionManager(CleanerLoginActivity.this);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    cleanerSessionManager.saveCleanerSession(uid, name, email, phone);

                    Toast.makeText(CleanerLoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(CleanerLoginActivity.this, CleanerDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(CleanerLoginActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CleanerLoginActivity.this, "Failed to load user info.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
