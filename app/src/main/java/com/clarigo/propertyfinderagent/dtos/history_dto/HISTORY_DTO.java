package com.clarigo.propertyfinderagent.dtos.history_dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HISTORY_DTO {

@SerializedName("response")
@Expose
private Boolean response;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("requestdata")
@Expose
private List<HISTORY_DATA_DTO> history_data_dtos = null;

/**
* No args constructor for use in serialization
*
*/
public HISTORY_DTO() {
}

/**
*
* @param response
* @param message
* @param history_data_dtos
* @param status
*/
public HISTORY_DTO(Boolean response, Integer status, String message, List<HISTORY_DATA_DTO> history_data_dtos) {
super();
this.response = response;
this.status = status;
this.message = message;
this.history_data_dtos = history_data_dtos;
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

public List<HISTORY_DATA_DTO> getHistory_data_dtos() {
return history_data_dtos;
}

public void setHistory_data_dtos(List<HISTORY_DATA_DTO> history_data_dtos) {
this.history_data_dtos = history_data_dtos;
}

}