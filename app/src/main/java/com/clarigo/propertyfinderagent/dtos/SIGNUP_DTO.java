package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.SerializedName;

public class SIGNUP_DTO {

    @SerializedName("response")
    public String response = "";
    @SerializedName("user_id")
    public String user_id = "";
    @SerializedName("message")
    public String message = "";

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
