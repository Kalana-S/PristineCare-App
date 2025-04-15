package com.example.firebaseapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.ui.auth.CustomerLoginActivity;
import com.example.firebaseapplication.ui.auth.CleanerLoginActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCustomer, btnCleaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomer = findViewById(R.id.btnCustomer);
        btnCleaner = findViewById(R.id.btnCleaner);

        btnCustomer.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CustomerLoginActivity.class));
        });

        btnCleaner.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CleanerLoginActivity.class));
        });

    }

}

