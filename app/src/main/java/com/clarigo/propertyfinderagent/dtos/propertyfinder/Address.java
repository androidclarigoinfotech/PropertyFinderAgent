
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("crossStreet")
    @Expose
    private String crossStreet;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("streetName")
    @Expose
    private String streetName;
    @SerializedName("streetNumberText")
    @Expose
    private String streetNumberText;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("streetNumber")
    @Expose
    private Integer streetNumber;
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("unit")
    @Expose
    private String unit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Address() {
    }

    /**
     * 
     * @param country
     * @param streetName
     * @param unit
     * @param city
     * @param streetNumber
     * @param postalCode
     * @param streetNumberText
     * @param state
     * @param crossStreet
     * @param full
     */
    public Address(String crossStreet, String state, String country, String postalCode, String streetName, String streetNumberText, String city, Integer streetNumber, String full, String unit) {
        super();
        this.crossStreet = crossStreet;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.streetNumberText = streetNumberText;
        this.city = city;
        this.streetNumber = streetNumber;
        this.full = full;
        this.unit = unit;
    }

    public String getCrossStreet() {
        return crossStreet;
    }

    public void setCrossStreet(String crossStreet) {
        this.crossStreet = crossStreet;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumberText() {
        return streetNumberText;
    }

    public void setStreetNumberText(String streetNumberText) {
        this.streetNumberText = streetNumberText;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
