
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Association {

    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amenities")
    @Expose
    private String amenities;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Association() {
    }

    /**
     * 
     * @param amenities
     * @param fee
     * @param name
     */
    public Association(Integer fee, String name, String amenities) {
        super();
        this.fee = fee;
        this.name = name;
        this.amenities = amenities;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

}
