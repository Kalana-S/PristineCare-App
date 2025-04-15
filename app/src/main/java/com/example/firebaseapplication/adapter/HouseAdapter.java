package com.example.firebaseapplication.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.House;
import java.util.List;
import android.graphics.Bitmap;
import android.util.Base64;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    public interface OnProceedClickListener {
        void onClick(House house);
    }

    private final List<House> houseList;
    private final OnProceedClickListener listener;

    public HouseAdapter(List<House> houseList, OnProceedClickListener listener) {
        this.houseList = houseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_house, parent, false);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House house = houseList.get(position);
        holder.textRooms.setText("Rooms: " + house.getNumberOfRooms());
        holder.textBaths.setText("Bathrooms: " + house.getNumberOfBathrooms());
        holder.textFloor.setText("Flooring: " + house.getFlooringType());

        if (house.getPhotoBase64() != null && !house.getPhotoBase64().isEmpty()) {
            byte[] decodedBytes = Base64.decode(house.getPhotoBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.houseImage.setImageBitmap(bitmap);
        }

        holder.proceedButton.setOnClickListener(v -> listener.onClick(house));
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    static class HouseViewHolder extends RecyclerView.ViewHolder {
        ImageView houseImage;
        TextView textRooms, textBaths, textFloor;
        Button proceedButton;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            houseImage = itemView.findViewById(R.id.houseImage);
            textRooms = itemView.findViewById(R.id.textRooms);
            textBaths = itemView.findViewById(R.id.textBaths);
            textFloor = itemView.findViewById(R.id.textFloor);
            proceedButton = itemView.findViewById(R.id.proceedButton);
        }
    }

}
