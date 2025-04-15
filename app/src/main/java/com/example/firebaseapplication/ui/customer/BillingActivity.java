package com.example.firebaseapplication.ui.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.utils.SessionManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.firebaseapplication.data.model.BillingJob;
import com.example.firebaseapplication.data.model.House;
import com.example.firebaseapplication.data.repository.BillingRepository;
import java.util.UUID;

public class BillingActivity extends AppCompatActivity {

    private TextView customerNameText, customerEmailText, roomsText, bathsText, floorText, totalPriceText;
    private ImageView houseImage;
    private Button proceedButton;
    private Spinner cleaningTypeSpinner;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        customerNameText = findViewById(R.id.customerNameText);
        customerEmailText = findViewById(R.id.customerEmailText);
        roomsText = findViewById(R.id.textRooms);
        bathsText = findViewById(R.id.textBaths);
        floorText = findViewById(R.id.textFloor);
        cleaningTypeSpinner = findViewById(R.id.cleaningTypeSpinner);
        totalPriceText = findViewById(R.id.totalPriceText);
        houseImage = findViewById(R.id.houseImageView);
        proceedButton = findViewById(R.id.proceedButton);

        String[] cleaningTypes = {
                "Regular Cleaning",
                "Deep Cleaning",
                "Spring Cleaning",
                "Specialized Cleaning"
        };

        ArrayAdapter<String> cleaningAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                cleaningTypes
        );
        cleaningAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cleaningTypeSpinner.setAdapter(cleaningAdapter);

        House house = (House) getIntent().getSerializableExtra("house");

        SessionManager session = new SessionManager(this);
        String name = session.getName();
        String email = session.getEmail();
        String customerId = session.getCustomerId();

        customerNameText.setText("Name: " + name);
        customerEmailText.setText("Email: " + email);
        roomsText.setText("Rooms: " + house.getNumberOfRooms());
        bathsText.setText("Bathrooms: " + house.getNumberOfBathrooms());
        floorText.setText("Flooring: " + house.getFlooringType());

        if (house.getPhotoBase64() != null && !house.getPhotoBase64().isEmpty()) {
            byte[] decodedBytes = Base64.decode(house.getPhotoBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            houseImage.setImageBitmap(bitmap);
        }

        cleaningTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCleaningType = cleaningTypes[position];
                int total = calculateBill(house, selectedCleaningType);
                totalPriceText.setText("Total Price: Rs. " + total);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        proceedButton.setOnClickListener(v -> {
            String jobId = UUID.randomUUID().toString();
            String selectedCleaningType = cleaningTypeSpinner.getSelectedItem().toString();

            BillingJob job = new BillingJob(
                    jobId, customerId, name, email,
                    house.getHouseId(), house.getNumberOfRooms(), house.getNumberOfBathrooms(),
                    house.getFlooringType(), selectedCleaningType, house.getPhotoBase64(),
                    calculateBill(house, selectedCleaningType)
            );

            new BillingRepository().submitJob(job, new BillingRepository.OnJobSubmitListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(BillingActivity.this, "New cleaning job added!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(BillingActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private int calculateBill(House house, String cleaningType) {
        int price = house.getNumberOfRooms() * 200 + house.getNumberOfBathrooms() * 300;

        switch (house.getFlooringType()) {
            case "Hardwood Flooring": price += 100; break;
            case "Tile Flooring": price += 200; break;
            case "Carpet Flooring": price += 500; break;
            case "Laminate Flooring": price += 150; break;
            case "Stone Flooring": price += 300; break;
        }

        switch (cleaningType) {
            case "Deep Cleaning": price += 400; break;
            case "Spring Cleaning": price += 300; break;
            case "Specialized Cleaning": price += 250; break;
            default: break;
        }

        return price;
    }

}



