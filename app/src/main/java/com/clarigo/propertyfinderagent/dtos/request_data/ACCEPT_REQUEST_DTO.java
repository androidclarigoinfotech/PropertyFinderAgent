package com.clarigo.propertyfinderagent.dtos.request_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ACCEPT_REQUEST_DTO {
    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public ACCEPT_REQUEST_DTO() {
    }

    /**
     * @param response
     * @param message
     */
    public ACCEPT_REQUEST_DTO(Boolean response, String message) {
        super();
        this.response = response;
        this.message = message;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
