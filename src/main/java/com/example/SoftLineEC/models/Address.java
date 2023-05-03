package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAddress;

    private String indexOfAddress;
    private String countyOfAddress;
    private String cityOfAddress;
    private String regionOfAddress;
    private String streetOfAddress;
    private String houseOfAddress;
    private String frameOfAddress;
    private String apartmentOfAddress;
    @JsonBackReference
    @ManyToOne(optional = true)
    private User userID;

    public Address(String indexOfAddress, String countyOfAddress, String cityOfAddress, String regionOfAddress, String streetOfAddress, String houseOfAddress, String frameOfAddress, String apartmentOfAddress, User userID) {
        this.indexOfAddress = indexOfAddress;
        this.countyOfAddress = countyOfAddress;
        this.cityOfAddress = cityOfAddress;
        this.regionOfAddress = regionOfAddress;
        this.streetOfAddress = streetOfAddress;
        this.houseOfAddress = houseOfAddress;
        this.frameOfAddress = frameOfAddress;
        this.apartmentOfAddress = apartmentOfAddress;
        this.userID = userID;
    }

    public Address() {
        this.cityOfAddress = "";
        this.houseOfAddress = "";
        this.countyOfAddress = "";
        this.regionOfAddress = "";
        this.frameOfAddress = "";
        this.indexOfAddress = "";
        this.streetOfAddress = "";
        this.apartmentOfAddress = "";
    }

    public long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(long idAddress) {
        this.idAddress = idAddress;
    }

    public String getIndexOfAddress() {
        return indexOfAddress;
    }

    public void setIndexOfAddress(String indexOfAddress) {
        this.indexOfAddress = indexOfAddress;
    }

    public String getCountyOfAddress() {
        return countyOfAddress;
    }

    public void setCountyOfAddress(String countyOfAddress) {
        this.countyOfAddress = countyOfAddress;
    }

    public String getCityOfAddress() {
        return cityOfAddress;
    }

    public void setCityOfAddress(String cityOfAddress) {
        this.cityOfAddress = cityOfAddress;
    }

    public String getRegionOfAddress() {
        return regionOfAddress;
    }

    public void setRegionOfAddress(String regionOfAddress) {
        this.regionOfAddress = regionOfAddress;
    }

    public String getStreetOfAddress() {
        return streetOfAddress;
    }

    public void setStreetOfAddress(String streetOfAddress) {
        this.streetOfAddress = streetOfAddress;
    }

    public String getHouseOfAddress() {
        return houseOfAddress;
    }

    public void setHouseOfAddress(String houseOfAddress) {
        this.houseOfAddress = houseOfAddress;
    }

    public String getFrameOfAddress() {
        return frameOfAddress;
    }

    public void setFrameOfAddress(String frameOfAddress) {
        this.frameOfAddress = frameOfAddress;
    }

    public String getApartmentOfAddress() {
        return apartmentOfAddress;
    }

    public void setApartmentOfAddress(String apartmentOfAddress) {
        this.apartmentOfAddress = apartmentOfAddress;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }
}
