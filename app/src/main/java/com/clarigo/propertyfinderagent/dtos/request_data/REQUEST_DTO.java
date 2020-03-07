
package com.clarigo.propertyfinderagent.dtos.request_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REQUEST_DTO {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("status")
    @Expose
    private Integer status;

    @Override
    public String toString() {
        return "REQUEST_DTO{" +
                "response=" + response +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", requestdata=" + requestdata +
                '}';
    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("requestdata")
    @Expose
    private REQUEST_DATA_DTO requestdata;

    /**
     * No args constructor for use in serialization
     *
     */
    public REQUEST_DTO() {
    }

    /**
     *
     * @param response
     * @param message
     * @param requestdata
     * @param status
     */
    public REQUEST_DTO(Boolean response, Integer status, String message, REQUEST_DATA_DTO requestdata) {
        super();
        this.response = response;
        this.status = status;
        this.message = message;
        this.requestdata = requestdata;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public REQUEST_DATA_DTO getRequestdata() {
        return requestdata;
    }

    public void setRequestdata(REQUEST_DATA_DTO requestdata) {
        this.requestdata = requestdata;
    }

}
