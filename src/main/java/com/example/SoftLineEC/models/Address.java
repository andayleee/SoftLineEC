package com.example.SoftLineEC.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
/**
 * Класс-сущность для работы с адресами пользователей.
 */
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
    /**
     * Конструктор класса.
     * @param indexOfAddress почтовый индекс адреса
     * @param countyOfAddress страна адреса
     * @param cityOfAddress город адреса
     * @param regionOfAddress регион адреса
     * @param streetOfAddress улица адреса
     * @param houseOfAddress дом адреса
     * @param frameOfAddress корпус адреса
     * @param apartmentOfAddress квартира адреса
     * @param userID пользователь, которому принадлежит адрес
     */
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
    /**
     * Пустой конструктор класса.
     */
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
    /**
     * Метод получения значения поля idAddress.
     * @return идентификатор адреса
     */
    public long getIdAddress() {
        return idAddress;
    }
    /**
     * Метод установки значения поля idAddress.
     * @param idAddress идентификатор адреса
     */
    public void setIdAddress(long idAddress) {
        this.idAddress = idAddress;
    }
    /**
     * Метод получения значения поля indexOfAddress.
     * @return почтовый индекс адреса
     */
    public String getIndexOfAddress() {
        return indexOfAddress;
    }
    /**
     * Метод установки значения поля indexOfAddress.
     * @param indexOfAddress почтовый индекс адреса
     */
    public void setIndexOfAddress(String indexOfAddress) {
        this.indexOfAddress = indexOfAddress;
    }
    /**
     * Метод получения значения поля countyOfAddress.
     * @return страна адреса
     */
    public String getCountyOfAddress() {
        return countyOfAddress;
    }
    /**
     * Метод установки значения поля countyOfAddress.
     * @param countyOfAddress страна адреса
     */
    public void setCountyOfAddress(String countyOfAddress) {
        this.countyOfAddress = countyOfAddress;
    }
    /**
     * Метод получения значения поля cityOfAddress.
     * @return город адреса
     */
    public String getCityOfAddress() {
        return cityOfAddress;
    }
    /**
     * Метод установки значения поля cityOfAddress.
     * @param cityOfAddress город адреса
     */
    public void setCityOfAddress(String cityOfAddress) {
        this.cityOfAddress = cityOfAddress;
    }
    /**
     * Метод получения значения поля regionOfAddress.
     * @return регион адреса
     */
    public String getRegionOfAddress() {
        return regionOfAddress;
    }
    /**
     * Метод установки значения поля regionOfAddress.
     * @param regionOfAddress регион адреса
     */
    public void setRegionOfAddress(String regionOfAddress) {
        this.regionOfAddress = regionOfAddress;
    }
    /**
     * Метод получения значения поля streetOfAddress.
     * @return улица адреса
     */
    public String getStreetOfAddress() {
        return streetOfAddress;
    }
    /**
     * Метод установки значения поля streetOfAddress.
     * @param streetOfAddress улица адреса
     */
    public void setStreetOfAddress(String streetOfAddress) {
        this.streetOfAddress = streetOfAddress;
    }
    /**
     * Метод получения значения поля houseOfAddress.
     * @return дом адреса
     */
    public String getHouseOfAddress() {
        return houseOfAddress;
    }
    /**
     * Метод установки значения поля houseOfAddress.
     * @param houseOfAddress дом адреса
     */
    public void setHouseOfAddress(String houseOfAddress) {
        this.houseOfAddress = houseOfAddress;
    }
    /**
     * Метод получения значения поля frameOfAddress.
     * @return корпус адреса
     */
    public String getFrameOfAddress() {
        return frameOfAddress;
    }
    /**
     * Метод установки значения поля frameOfAddress.
     * @param frameOfAddress корпус адреса
     */
    public void setFrameOfAddress(String frameOfAddress) {
        this.frameOfAddress = frameOfAddress;
    }
    /**
     * Метод получения значения поля apartmentOfAddress.
     * @return квартира адреса
     */
    public String getApartmentOfAddress() {
        return apartmentOfAddress;
    }
    /**
     * Метод установки значения поля apartmentOfAddress.
     * @param apartmentOfAddress квартира адреса
     */
    public void setApartmentOfAddress(String apartmentOfAddress) {
        this.apartmentOfAddress = apartmentOfAddress;
    }
    /**
     * Метод получения значения поля userID.
     * @return пользователь, которому принадлежит адрес
     */
    public User getUserID() {
        return userID;
    }
    /**
     * Метод установки значения поля userID.
     * @param userID пользователь, которому принадлежит адрес
     */
    public void setUserID(User userID) {
        this.userID = userID;
    }
}
