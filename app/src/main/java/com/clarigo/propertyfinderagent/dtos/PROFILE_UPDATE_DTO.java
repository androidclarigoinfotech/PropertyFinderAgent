package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PROFILE_UPDATE_DTO {

    @SerializedName("responce")
    @Expose
    public String responce = "";
    @SerializedName("message")
    @Expose
    public String message = "";

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
