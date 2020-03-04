
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parking {

    @SerializedName("leased")
    @Expose
    private Object leased;
    @SerializedName("spaces")
    @Expose
    private Integer spaces;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Parking() {
    }

    /**
     * 
     * @param leased
     * @param spaces
     * @param description
     */
    public Parking(Object leased, Integer spaces, String description) {
        super();
        this.leased = leased;
        this.spaces = spaces;
        this.description = description;
    }

    public Object getLeased() {
        return leased;
    }

    public void setLeased(Object leased) {
        this.leased = leased;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
