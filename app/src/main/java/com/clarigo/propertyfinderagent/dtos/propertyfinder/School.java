
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName("middleSchool")
    @Expose
    private String middleSchool;
    @SerializedName("highSchool")
    @Expose
    private String highSchool;
    @SerializedName("elementarySchool")
    @Expose
    private String elementarySchool;
    @SerializedName("district")
    @Expose
    private Object district;

    /**
     * No args constructor for use in serialization
     * 
     */
    public School() {
    }

    /**
     * 
     * @param elementarySchool
     * @param district
     * @param highSchool
     * @param middleSchool
     */
    public School(String middleSchool, String highSchool, String elementarySchool, Object district) {
        super();
        this.middleSchool = middleSchool;
        this.highSchool = highSchool;
        this.elementarySchool = elementarySchool;
        this.district = district;
    }

    public String getMiddleSchool() {
        return middleSchool;
    }

    public void setMiddleSchool(String middleSchool) {
        this.middleSchool = middleSchool;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getElementarySchool() {
        return elementarySchool;
    }

    public void setElementarySchool(String elementarySchool) {
        this.elementarySchool = elementarySchool;
    }

    public Object getDistrict() {
        return district;
    }

    public void setDistrict(Object district) {
        this.district = district;
    }

}
