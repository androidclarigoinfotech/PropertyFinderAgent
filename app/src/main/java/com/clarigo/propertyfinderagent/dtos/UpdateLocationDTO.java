package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLocationDTO {

@SerializedName("response")
@Expose
private Boolean response;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;

/**
* No args constructor for use in serialization
*
*/
public UpdateLocationDTO() {
}

/**
*
* @param response
* @param message
* @param status
*/
public UpdateLocationDTO(Boolean response, Integer status, String message) {
super();
this.response = response;
this.status = status;
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

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}