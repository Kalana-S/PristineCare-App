package com.example.firebaseapplication.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebaseapplication.R;
import com.example.firebaseapplication.data.model.BillingJob;
import java.util.List;

public class AcceptedJobAdapter extends RecyclerView.Adapter<AcceptedJobAdapter.JobViewHolder> {

    private final List<BillingJob> jobList;

    public AcceptedJobAdapter(List<BillingJob> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accepted_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        BillingJob job = jobList.get(position);
        holder.cleaningTypeText.setText("Cleaning Type: " + job.getCleaningType());
        holder.totalPriceText.setText("Total Price: " + job.getTotalPrice());
        holder.cleanerNameText.setText("Cleaner Name: " + job.getCleanerName());
        holder.cleanerEmailText.setText("Cleaner Email: " + job.getCleanerEmail());
        holder.statusText.setText("Status: " + job.getStatus());

        if (job.getPhotoBase64() != null && !job.getPhotoBase64().isEmpty()) {
            byte[] imageBytes = Base64.decode(job.getPhotoBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.houseImage.setImageBitmap(bitmap);
        }
        if ("Completed".equals(job.getStatus())) {
            holder.reviewButton.setVisibility(View.VISIBLE);
            holder.reviewButton.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), com.example.firebaseapplication.ui.customer.ReviewActivity.class);
                intent.putExtra("jobId", job.getJobId());
                intent.putExtra("cleanerId", job.getCleanerId());
                intent.putExtra("cleanerName", job.getCleanerName());
                holder.itemView.getContext().startActivity(intent);
            });
        } else {
            holder.reviewButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView houseImage;
        TextView cleaningTypeText, totalPriceText, cleanerNameText, cleanerEmailText, statusText;
        Button reviewButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            houseImage = itemView.findViewById(R.id.houseImage);
            cleaningTypeText = itemView.findViewById(R.id.cleaningTypeText);
            totalPriceText = itemView.findViewById(R.id.totalPriceText);
            cleanerNameText = itemView.findViewById(R.id.cleanerNameText);
            cleanerEmailText = itemView.findViewById(R.id.cleanerEmailText);
            statusText = itemView.findViewById(R.id.statusText);
            reviewButton = itemView.findViewById(R.id.reviewButton);
        }
    }

}
