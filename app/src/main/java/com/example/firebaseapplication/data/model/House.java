package com.example.firebaseapplication.data.model;

import java.io.Serializable;

public class House  implements Serializable {

    private String houseId;
    private String ownerId;
    private int numberOfRooms;
    private int numberOfBathrooms;
    private String flooringType;
    private String photoBase64;

    public House() {}

    public House(String houseId, String ownerId, int numberOfRooms, int numberOfBathrooms,
                 String flooringType, String photoBase64) {
        this.houseId = houseId;
        this.ownerId = ownerId;
        this.numberOfRooms = numberOfRooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.flooringType = flooringType;
        this.photoBase64 = photoBase64;
    }

    public String getHouseId() { return houseId; }
    public void setHouseId(String houseId) { this.houseId = houseId; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public int getNumberOfBathrooms() { return numberOfBathrooms; }
    public void setNumberOfBathrooms(int numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms; }

    public String getFlooringType() { return flooringType; }
    public void setFlooringType(String flooringType) { this.flooringType = flooringType; }

    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }

}
