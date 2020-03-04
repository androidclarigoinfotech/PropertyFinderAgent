package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rohits on 15/4/19.
 */

public class NotificationDTO implements Serializable {
    @SerializedName("typeid")
    @Expose
    private String typeid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userid")
    @Expose
    private String userid;

    /**
     * No args constructor for use in serialization
     */
    public NotificationDTO() {
    }

    /**
     * @param typeid
     * @param id
     * @param type
     * @param title
     * @param message
     * @param userid
     */
    public NotificationDTO(String typeid, String id, String type, String title, String message, String userid) {
        super();
        this.typeid = typeid;
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.userid = userid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
