package com.example.firebaseapplication.ui.customer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.House;
import com.example.firebaseapplication.data.repository.HouseRepository;
import com.example.firebaseapplication.utils.SessionManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddHouseActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView nameText, emailText;
    private EditText roomsInput, bathsInput;
    private Spinner floorSpinner;
    private ImageView houseImageView;
    private Button chooseImageButton, submitButton;
    private String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        nameText = findViewById(R.id.customerName);
        emailText = findViewById(R.id.customerEmail);
        roomsInput = findViewById(R.id.editRooms);
        bathsInput = findViewById(R.id.editBaths);
        floorSpinner = findViewById(R.id.spinnerFloor);
        houseImageView = findViewById(R.id.houseImageView);
        chooseImageButton = findViewById(R.id.chooseImageButton);
        submitButton = findViewById(R.id.submitButton);

        String[] flooringTypes = {
                "Hardwood Flooring",
                "Tile Flooring",
                "Carpet Flooring",
                "Laminate Flooring",
                "Stone Flooring"
        };

        ArrayAdapter<String> floorAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                flooringTypes
        );
        floorAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        floorSpinner.setAdapter(floorAdapter);

        SessionManager sessionManager = new SessionManager(this);
        String ownerId = sessionManager.getCustomerId();

        nameText.setText(sessionManager.getName());
        emailText.setText(sessionManager.getEmail());

        chooseImageButton.setOnClickListener(v -> openImagePicker());

        submitButton.setOnClickListener(v -> {
            String houseId = UUID.randomUUID().toString();
            int rooms = Integer.parseInt(roomsInput.getText().toString());
            int baths = Integer.parseInt(bathsInput.getText().toString());
            String floorType = floorSpinner.getSelectedItem().toString();

            House house = new House(houseId, ownerId, rooms, baths, floorType, encodedImage);
            HouseRepository houseRepo = new HouseRepository();

            houseRepo.addHouse(house, houseId, new HouseRepository.OnHouseAddedListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AddHouseActivity.this, "House added!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(AddHouseActivity.this, "Failed: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            houseImageView.setImageURI(imageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                encodedImage = encodeImageToBase64(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

}
