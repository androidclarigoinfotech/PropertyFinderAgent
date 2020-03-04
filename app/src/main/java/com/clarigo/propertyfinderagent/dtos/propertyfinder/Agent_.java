
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agent_ {

    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("contact")
    @Expose
    private Object contact;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Agent_() {
    }

    /**
     * 
     * @param lastName
     * @param firstName
     * @param contact
     * @param id
     */
    public Agent_(String lastName, Object contact, String firstName, String id) {
        super();
        this.lastName = lastName;
        this.contact = contact;
        this.firstName = firstName;
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getContact() {
        return contact;
    }

    public void setContact(Object contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
