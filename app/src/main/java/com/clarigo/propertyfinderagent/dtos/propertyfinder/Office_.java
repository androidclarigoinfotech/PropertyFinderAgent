
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Office_ {

    @SerializedName("contact")
    @Expose
    private Object contact;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("servingName")
    @Expose
    private String servingName;
    @SerializedName("brokerid")
    @Expose
    private String brokerid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Office_() {
    }

    /**
     * 
     * @param brokerid
     * @param contact
     * @param name
     * @param servingName
     */
    public Office_(Object contact, String name, String servingName, String brokerid) {
        super();
        this.contact = contact;
        this.name = name;
        this.servingName = servingName;
        this.brokerid = brokerid;
    }

    public Object getContact() {
        return contact;
    }

    public void setContact(Object contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServingName() {
        return servingName;
    }

    public void setServingName(String servingName) {
        this.servingName = servingName;
    }

    public String getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(String brokerid) {
        this.brokerid = brokerid;
    }

}
