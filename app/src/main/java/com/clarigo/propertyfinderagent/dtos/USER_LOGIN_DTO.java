
package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class USER_LOGIN_DTO {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    @Expose
    private List<USER_LOGIN_DATA_DTO> data = null;

    /**
     * No args constructor for use in serialization
     */
    public USER_LOGIN_DTO() {
    }

    /**
     * @param data
     * @param response
     * @param status
     */
    public USER_LOGIN_DTO(Boolean response, Integer status, List<USER_LOGIN_DATA_DTO> data) {
        super();
        this.response = response;
        this.status = status;
        this.data = data;
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

    public List<USER_LOGIN_DATA_DTO> getData() {
        return data;
    }

    public void setData(List<USER_LOGIN_DATA_DTO> data) {
        this.data = data;
    }

}
