package com.example.firebaseapplication.data.model;

public class BillingJob {

    private String jobId;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String houseId;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private String flooringType;
    private String cleaningType;
    private String photoBase64;
    private int totalPrice;
    private String status;
    private String cleanerName;
    private String cleanerEmail;
    private String cleanerId;

    public BillingJob() {}

    public BillingJob(String jobId, String customerId, String customerName, String customerEmail,
                      String houseId, int numberOfRooms, int numberOfBathrooms,
                      String flooringType, String cleaningType, String photoBase64, int totalPrice) {
        this.jobId = jobId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.houseId = houseId;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.flooringType = flooringType;
        this.cleaningType = cleaningType;
        this.photoBase64 = photoBase64;
        this.totalPrice = totalPrice;
    }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getCustomerId() { return  customerId; }
    public  void  setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public  void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getHouseId() { return  houseId; }
    public void  setHouseId(String houseId) { this.houseId = houseId; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public int getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(int numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms; }

    public String getFlooringType() { return flooringType; }
    public void setFlooringType(String flooringType) { this.flooringType = flooringType; }

    public String getCleaningType() { return cleaningType; }
    public void setCleaningType(String cleaningType) { this.cleaningType = cleaningType; }

    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCleanerName() { return cleanerName; }
    public  void setCleanerName(String cleanerName) { this.cleanerName = cleanerName; }

    public String getCleanerEmail() { return cleanerEmail; }
    public void setCleanerEmail(String cleanerEmail) { this.cleanerEmail = cleanerEmail; }

    public String getCleanerId() { return cleanerId; }
    public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }

}
