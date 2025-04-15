package com.example.firebaseapplication.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.adapter.HouseAdapter;
import com.example.firebaseapplication.data.model.House;
import com.example.firebaseapplication.utils.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerHouseListActivity extends AppCompatActivity {

    private TextView customerNameText, customerEmailText;
    private RecyclerView houseRecyclerView;
    private HouseAdapter adapter;
    private List<House> houseList = new ArrayList<>();
    private DatabaseReference houseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_house_list);

        customerNameText = findViewById(R.id.customerNameText);
        customerEmailText = findViewById(R.id.customerEmailText);
        houseRecyclerView = findViewById(R.id.houseRecyclerView);

        SessionManager session = new SessionManager(this);
        customerNameText.setText("Welcome, " + session.getName());
        customerEmailText.setText(session.getEmail());

        adapter = new HouseAdapter(houseList, this::onProceedClicked);
        houseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        houseRecyclerView.setAdapter(adapter);

        houseRef = FirebaseDatabase.getInstance().getReference("houses");
        loadCustomerHouses(session.getCustomerId());
    }

    private void loadCustomerHouses(String customerId) {
        houseRef.orderByChild("ownerId").equalTo(customerId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                        houseList.clear();
                        for (DataSnapshot houseSnap : snapshot.getChildren()) {
                            House house = houseSnap.getValue(House.class);
                            houseList.add(house);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CustomerHouseListActivity.this,
                                "Error loading houses", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onProceedClicked(House house) {
        Intent intent = new Intent(this, BillingActivity.class);
        intent.putExtra("house", house);
        startActivity(intent);
    }

}

