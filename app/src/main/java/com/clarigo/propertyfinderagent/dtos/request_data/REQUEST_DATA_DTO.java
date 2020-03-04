
package com.clarigo.propertyfinderagent.dtos.request_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class REQUEST_DATA_DTO {

    @SerializedName("ag_id")
    @Expose
    private String agId;
    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("loca")
    @Expose
    private String loca;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reasons")
    @Expose
    private String reasons;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("pickup_type")
    @Expose
    private String pickupType;
    @SerializedName("acceptdate")
    @Expose
    private String acceptdate;
    @SerializedName("canceldate")
    @Expose
    private String canceldate;
    @SerializedName("types")
    @Expose
    private String types;

    @SerializedName("timmer_time")
    @Expose
    private String timmer_time;
    @SerializedName("req_time")
    @Expose
    private String req_time;


    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("emr_num")
    @Expose
    private String emrNum;
    @SerializedName("profilepic")
    @Expose
    private String profilepic;
    @SerializedName("userlat")
    @Expose
    private String userlat;
    @SerializedName("userlng")
    @Expose
    private String userlng;
    @SerializedName("location")
    @Expose
    private String location;

    /**
     * No args constructor for use in serialization
     */
    public REQUEST_DATA_DTO() {
    }

    /**
     * @param userlng
     * @param agentId
     * @param types
     * @param reasons
     * @param lng
     * @param timezone
     * @param profilepic
     * @param fullName
     * @param agId
     * @param userId
     * @param timmer_time
     * @param req_time
     * @param pickupType
     * @param userlat
     * @param canceldate
     * @param acceptdate
     * @param emrNum
     * @param loca
     * @param createdDate
     * @param phone
     * @param location
     * @param propertyId
     * @param lat
     * @param email
     * @param status
     */
    public REQUEST_DATA_DTO(String agId, String agentId, String propertyId, String lat, String lng, String loca, String status, String reasons, String userId, String pickupType, String acceptdate, String canceldate, String types, String timmer_time, String req_time, String timezone, String createdDate, String fullName, String email, String phone, String emrNum, String profilepic, String userlat, String userlng, String location) {
        super();
        this.agId = agId;
        this.agentId = agentId;
        this.propertyId = propertyId;
        this.lat = lat;
        this.lng = lng;
        this.loca = loca;
        this.status = status;
        this.reasons = reasons;
        this.userId = userId;
        this.pickupType = pickupType;
        this.acceptdate = acceptdate;
        this.canceldate = canceldate;
        this.types = types;
        this.timmer_time = timmer_time;
        this.req_time = req_time;
        this.timezone = timezone;
        this.createdDate = createdDate;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.emrNum = emrNum;
        this.profilepic = profilepic;
        this.userlat = userlat;
        this.userlng = userlng;
        this.location = location;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public String getAcceptdate() {
        return acceptdate;
    }

    public void setAcceptdate(String acceptdate) {
        this.acceptdate = acceptdate;
    }

    public String getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(String canceldate) {
        this.canceldate = canceldate;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTimmer_time() {
        return timmer_time;
    }

    public void setTimmer_time(String timmer_time) {
        this.timmer_time = timmer_time;
    }

    public String getReq_time() {
        return req_time;
    }

    public void setReq_time(String req_time) {
        this.req_time = req_time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmrNum() {
        return emrNum;
    }

    public void setEmrNum(String emrNum) {
        this.emrNum = emrNum;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserlat() {
        return userlat;
    }

    public void setUserlat(String userlat) {
        this.userlat = userlat;
    }

    public String getUserlng() {
        return userlng;
    }

    public void setUserlng(String userlng) {
        this.userlng = userlng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
