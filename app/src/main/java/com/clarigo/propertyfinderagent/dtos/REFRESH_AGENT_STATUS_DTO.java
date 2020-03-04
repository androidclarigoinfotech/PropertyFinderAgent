package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REFRESH_AGENT_STATUS_DTO {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public REFRESH_AGENT_STATUS_DTO() {
    }

    /**
     * @param response
     * @param onlineStatus
     * @param message
     * @param status
     */
    public REFRESH_AGENT_STATUS_DTO(Boolean response, Integer status, String onlineStatus, String message) {
        super();
        this.response = response;
        this.status = status;
        this.onlineStatus = onlineStatus;
        this.message = message;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
