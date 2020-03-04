package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.SerializedName;

public class Mobile_DTO {


    @SerializedName("message")
    public String message = "";
    @SerializedName("otp")
    public String otp = "";
    @SerializedName("id")
    public String id = "";
    @SerializedName("status")
    public String status = "";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
