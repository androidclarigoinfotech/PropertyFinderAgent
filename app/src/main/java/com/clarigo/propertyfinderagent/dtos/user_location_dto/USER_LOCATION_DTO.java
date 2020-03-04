package com.clarigo.propertyfinderagent.dtos.user_location_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    USER_LOCATION_DTO {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private User_Location_Data_Dto user_location_data_dto;

    /**
     * No args constructor for use in serialization
     */
    public USER_LOCATION_DTO() {
    }

    /**
     * @param user_location_data_dto
     * @param response
     * @param message
     * @param status
     */
    public USER_LOCATION_DTO(Boolean response, Integer status, String message, User_Location_Data_Dto user_location_data_dto) {
        super();
        this.response = response;
        this.status = status;
        this.message = message;
        this.user_location_data_dto = user_location_data_dto;
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

    public User_Location_Data_Dto getData() {
        return user_location_data_dto;
    }

    public void setData(User_Location_Data_Dto user_location_data_dto) {
        this.user_location_data_dto = user_location_data_dto;
    }

}