
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geo {

    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("marketArea")
    @Expose
    private String marketArea;
    @SerializedName("directions")
    @Expose
    private String directions;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geo() {
    }

    /**
     * 
     * @param lng
     * @param directions
     * @param marketArea
     * @param county
     * @param lat
     */
    public Geo(String county, Double lat, Double lng, String marketArea, String directions) {
        super();
        this.county = county;
        this.lat = lat;
        this.lng = lng;
        this.marketArea = marketArea;
        this.directions = directions;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getMarketArea() {
        return marketArea;
    }

    public void setMarketArea(String marketArea) {
        this.marketArea = marketArea;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

}
