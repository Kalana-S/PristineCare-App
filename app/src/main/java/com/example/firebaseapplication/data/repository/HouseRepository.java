package com.example.firebaseapplication.data.repository;

import com.example.firebaseapplication.data.model.House;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HouseRepository {

    private final DatabaseReference houseRef;

    public HouseRepository() {
        houseRef = FirebaseDatabase.getInstance().getReference("houses");
    }

    public void addHouse(House house, String houseId, OnHouseAddedListener listener) {
        houseRef.child(houseId).setValue(house)
                .addOnSuccessListener(unused -> listener.onSuccess())
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public interface OnHouseAddedListener {
        void onSuccess();
        void onFailure(String error);
    }

}


