package com.example.firebaseapplication.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.utils.SessionManager;
import android.widget.TextView;
import android.widget.Button;

public class CustomerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        TextView nameText = findViewById(R.id.dashboardName);
        TextView emailText = findViewById(R.id.dashboardEmail);
        Button addHouseButton = findViewById(R.id.btnAddHouse);
        Button viewHouseListButton = findViewById(R.id.btnHouseList);
        Button acceptJobListButton = findViewById(R.id.btnAcceptJobList);
        Button viewBillPrices = findViewById(R.id. btnViewBillPrices);

        SessionManager session = new SessionManager(this);

        nameText.setText(session.getName());
        emailText.setText(session.getEmail());

        addHouseButton.setOnClickListener(view -> {

            startActivity(new Intent(CustomerDashboardActivity.this, AddHouseActivity.class));

        });

        viewHouseListButton.setOnClickListener(view -> {

            startActivity(new Intent(CustomerDashboardActivity.this, CustomerHouseListActivity.class));

        });

        acceptJobListButton.setOnClickListener(view -> {

            startActivity(new Intent(CustomerDashboardActivity.this, AcceptedJobListActivity.class));

        });

        viewBillPrices.setOnClickListener(view -> {

            startActivity(new Intent(CustomerDashboardActivity.this, BillPriceActivity.class));

        });

    }

}