package com.example.firebaseapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.viewmodel.CleanerAuthViewModel;


public class CleanerRegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, phoneInput, passwordInput;
    private Button registerButton;
    private CleanerAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_register);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        viewModel = new ViewModelProvider(this).get(CleanerAuthViewModel.class);

        viewModel.getRegistrationSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(CleanerRegisterActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CleanerLoginActivity.class));
                finish();
            }
        });

        viewModel.getRegistrationError().observe(this, error -> {
            Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
        });

        registerButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String password = passwordInput.getText().toString();

            viewModel.register(name, email, phone, password);
        });
    }

}

