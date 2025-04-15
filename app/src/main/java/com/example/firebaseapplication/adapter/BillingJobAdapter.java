package com.example.firebaseapplication.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.BillingJob;

import java.util.Arrays;
import java.util.List;
import android.graphics.Bitmap;
import android.util.Base64;

public class BillingJobAdapter extends RecyclerView.Adapter<BillingJobAdapter.BillingJobViewHolder> {

    public interface OnProceedClickListener {
        void onClick(BillingJob billingJob);
    }

    private final List<BillingJob> billingJobList;
    private final BillingJobAdapter.OnProceedClickListener listener;

    public BillingJobAdapter(List<BillingJob> billingJobList, BillingJobAdapter.OnProceedClickListener listener) {
        this.billingJobList = billingJobList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillingJobAdapter.BillingJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);
        return new BillingJobAdapter.BillingJobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingJobAdapter.BillingJobViewHolder holder, int position) {
        BillingJob billingJob = billingJobList.get(position);
        holder.textRooms.setText("Rooms: " + billingJob.getNumberOfRooms());
        holder.textBaths.setText("Bathrooms: " + billingJob.getNumberOfBathrooms());
        holder.textFloor.setText("Flooring: " + billingJob.getFlooringType());
        holder.textClean.setText("Cleaning: " + billingJob.getCleaningType());
        holder.textCustomerName.setText("Customer Name: " + billingJob.getCustomerName());
        holder.textCustomerEmail.setText("Customer Email: " + billingJob.getCustomerEmail());
        holder.textTotalPrice.setText("Total Price: " + billingJob.getTotalPrice());

        if (billingJob.getPhotoBase64() != null && !billingJob.getPhotoBase64().isEmpty()) {
            byte[] decodedBytes = Base64.decode(billingJob.getPhotoBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.houseImage.setImageBitmap(bitmap);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                holder.itemView.getContext(),
                R.layout.spinner_item,
                Arrays.asList("Scheduled", "Completed", "Declined")
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        holder.statusSpinner.setAdapter(spinnerAdapter);

        holder.acceptButton.setOnClickListener(v -> {
            String selectedStatus = holder.statusSpinner.getSelectedItem().toString();
            billingJob.setStatus(selectedStatus);
            listener.onClick(billingJob);
        });
    }

    @Override
    public int getItemCount() {
        return billingJobList.size();
    }

    static class BillingJobViewHolder extends RecyclerView.ViewHolder {
        ImageView houseImage;
        TextView textRooms, textBaths, textFloor, textClean, textCustomerName, textCustomerEmail, textTotalPrice;
        Button acceptButton;
        Spinner statusSpinner;


        public BillingJobViewHolder(@NonNull View itemView) {
            super(itemView);
            houseImage = itemView.findViewById(R.id.houseImage);
            textRooms = itemView.findViewById(R.id.textRooms);
            textBaths = itemView.findViewById(R.id.textBaths);
            textFloor = itemView.findViewById(R.id.textFloor);
            textClean = itemView.findViewById(R.id.textClean);
            textCustomerName = itemView.findViewById(R.id.textCustomerName);
            textCustomerEmail = itemView.findViewById(R.id.textCustomerEmail);
            textTotalPrice = itemView.findViewById(R.id.textTotalPrice);
            statusSpinner = itemView.findViewById(R.id.statusSpinner);
            acceptButton = itemView.findViewById(R.id.acceptButton);
        }
    }

}
