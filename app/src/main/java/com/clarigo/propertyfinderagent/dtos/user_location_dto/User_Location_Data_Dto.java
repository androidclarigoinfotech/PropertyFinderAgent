package com.clarigo.propertyfinderagent.dtos.user_location_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_Location_Data_Dto {
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    /**
     * No args constructor for use in serialization
     *
     */
    public User_Location_Data_Dto() {
    }

    /**
     *
     * @param lng
     * @param lat
     */
    public User_Location_Data_Dto(String lat, String lng) {
        super();
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
