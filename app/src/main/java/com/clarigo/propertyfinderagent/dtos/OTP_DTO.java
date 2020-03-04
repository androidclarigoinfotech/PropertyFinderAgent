package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.SerializedName;

public class OTP_DTO {
    @SerializedName("response")
    public String response = "";
    @SerializedName("message")
    public String message = "";
    @SerializedName("status")
    public String status = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
