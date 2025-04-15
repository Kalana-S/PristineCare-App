package com.example.firebaseapplication.data.model;

public class Review {

    private String reviewId;
    private String jobId;
    private String cleanerId;
    private String cleanerName;
    private String customerId;
    private String customerName;
    private float rating;
    private String comment;
    private long timestamp;

    public Review() {}

    public Review(String reviewId, String jobId, String cleanerId, String cleanerName, String customerId, String customerName, float rating, String comment, long timestamp) {
        this.reviewId = reviewId;
        this.jobId = jobId;
        this.cleanerId = cleanerId;
        this.cleanerName = cleanerName;
        this.customerId = customerId;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getCleanerId() { return cleanerId; }
    public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }

    public String getCleanerName() { return cleanerName; }
    public void setCleanerName(String cleanerName) { this.cleanerName = cleanerName; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

}
