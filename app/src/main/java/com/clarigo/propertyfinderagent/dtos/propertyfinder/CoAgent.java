
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoAgent {

    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("contact")
    @Expose
    private Object contact;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CoAgent() {
    }

    /**
     * 
     * @param lastName
     * @param firstName
     * @param contact
     * @param id
     */
    public CoAgent(Object lastName, Object contact, Object firstName, String id) {
        super();
        this.lastName = lastName;
        this.contact = contact;
        this.firstName = firstName;
        this.id = id;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getContact() {
        return contact;
    }

    public void setContact(Object contact) {
        this.contact = contact;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
