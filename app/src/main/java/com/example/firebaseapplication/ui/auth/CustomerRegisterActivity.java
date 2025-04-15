package com.example.firebaseapplication.ui.auth;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.*;
import com.example.firebaseapplication.R;
import androidx.lifecycle.ViewModelProvider;
import com.example.firebaseapplication.viewmodel.CustomerAuthViewModel;

public class CustomerRegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, phoneInput, addressInput, passwordInput;
    private Button registerButton;
    private CustomerAuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);

        viewModel = new ViewModelProvider(this).get(CustomerAuthViewModel.class);

        viewModel.getRegistrationSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(CustomerRegisterActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CustomerLoginActivity.class));
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
            String address = addressInput.getText().toString();
            String password = passwordInput.getText().toString();

            viewModel.register(name, email, phone, address, password);
        });
    }

}


