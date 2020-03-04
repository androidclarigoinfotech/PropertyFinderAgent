
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Office {

    @SerializedName("contact")
    @Expose
    private Object contact;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("servingName")
    @Expose
    private Object servingName;
    @SerializedName("brokerid")
    @Expose
    private Object brokerid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Office() {
    }

    /**
     * 
     * @param brokerid
     * @param contact
     * @param name
     * @param servingName
     */
    public Office(Object contact, Object name, Object servingName, Object brokerid) {
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

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getServingName() {
        return servingName;
    }

    public void setServingName(Object servingName) {
        this.servingName = servingName;
    }

    public Object getBrokerid() {
        return brokerid;
    }

    public void setBrokerid(Object brokerid) {
        this.brokerid = brokerid;
    }

}
