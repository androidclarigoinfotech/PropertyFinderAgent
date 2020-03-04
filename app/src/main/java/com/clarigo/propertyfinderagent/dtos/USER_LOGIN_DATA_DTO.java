
package com.clarigo.propertyfinderagent.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class USER_LOGIN_DATA_DTO {

    @SerializedName("agent_id")
    @Expose
    private String agentId;
    @SerializedName("agent_name")
    @Expose
    private String agentName;
    @SerializedName("agent_email")
    @Expose
    private String agentEmail;
    @SerializedName("dre")
    @Expose
    private String dre;
    @SerializedName("MLS_ID")
    @Expose
    private String mLSID;
    @SerializedName("rmls")
    @Expose
    private String rmls;
    @SerializedName("aor")
    @Expose
    private String aor;
    @SerializedName("ekey")
    @Expose
    private String ekey;
    @SerializedName("realstatelicense")
    @Expose
    private String realstatelicense;
    @SerializedName("dri_license")
    @Expose
    private String driLicense;
    @SerializedName("agent_phone")
    @Expose
    private String agentPhone;
    @SerializedName("agent_pass")
    @Expose
    private String agentPass;
    @SerializedName("profilepic")
    @Expose
    private String profilepic;
    @SerializedName("request_status")
    @Expose
    private String requestStatus;
    @SerializedName("agent_status")
    @Expose
    private String agentStatus;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("visited_site")
    @Expose
    private String visitedSite;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("aplan_id")
    @Expose
    private String aplanId;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("purchase_on")
    @Expose
    private String purchaseOn;
    @SerializedName("expiry_dates")
    @Expose
    private String expiryDates;

    /**
     * No args constructor for use in serialization
     */
    public USER_LOGIN_DATA_DTO() {
    }

    /**
     * @param agentId
     * @param agentPhone
     * @param agentEmail
     * @param aor
     * @param profilepic
     * @param driLicense
     * @param realstatelicense
     * @param dre
     * @param lat
     * @param address
     * @param lng
     * @param visitedSite
     * @param agentName
     * @param mLSID
     * @param agentPass
     * @param agentStatus
     * @param deviceToken
     * @param expiryDates
     * @param createdDate
     * @param ekey
     * @param rmls
     * @param location
     * @param validity
     * @param purchaseOn
     * @param requestStatus
     * @param aplanId
     */
    public USER_LOGIN_DATA_DTO(String agentId, String agentName, String agentEmail, String dre, String mLSID, String rmls, String aor, String ekey, String realstatelicense, String driLicense, String agentPhone, String agentPass, String profilepic, String requestStatus, String agentStatus, String address, String lat, String lng, String location, String deviceToken, String visitedSite, String createdDate, String aplanId, String validity, String purchaseOn, String expiryDates) {
        super();
        this.agentId = agentId;
        this.agentName = agentName;
        this.agentEmail = agentEmail;
        this.dre = dre;
        this.mLSID = mLSID;
        this.rmls = rmls;
        this.aor = aor;
        this.ekey = ekey;
        this.realstatelicense = realstatelicense;
        this.driLicense = driLicense;
        this.agentPhone = agentPhone;
        this.agentPass = agentPass;
        this.profilepic = profilepic;
        this.requestStatus = requestStatus;
        this.agentStatus = agentStatus;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
        this.deviceToken = deviceToken;
        this.visitedSite = visitedSite;
        this.createdDate = createdDate;
        this.aplanId = aplanId;
        this.validity = validity;
        this.purchaseOn = purchaseOn;
        this.expiryDates = expiryDates;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getDre() {
        return dre;
    }

    public void setDre(String dre) {
        this.dre = dre;
    }

    public String getMLSID() {
        return mLSID;
    }

    public void setMLSID(String mLSID) {
        this.mLSID = mLSID;
    }

    public String getRmls() {
        return rmls;
    }

    public void setRmls(String rmls) {
        this.rmls = rmls;
    }

    public String getAor() {
        return aor;
    }

    public void setAor(String aor) {
        this.aor = aor;
    }

    public String getEkey() {
        return ekey;
    }

    public void setEkey(String ekey) {
        this.ekey = ekey;
    }

    public String getRealstatelicense() {
        return realstatelicense;
    }

    public void setRealstatelicense(String realstatelicense) {
        this.realstatelicense = realstatelicense;
    }

    public String getDriLicense() {
        return driLicense;
    }

    public void setDriLicense(String driLicense) {
        this.driLicense = driLicense;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getAgentPass() {
        return agentPass;
    }

    public void setAgentPass(String agentPass) {
        this.agentPass = agentPass;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getVisitedSite() {
        return visitedSite;
    }

    public void setVisitedSite(String visitedSite) {
        this.visitedSite = visitedSite;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAplanId() {
        return aplanId;
    }

    public void setAplanId(String aplanId) {
        this.aplanId = aplanId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPurchaseOn() {
        return purchaseOn;
    }

    public void setPurchaseOn(String purchaseOn) {
        this.purchaseOn = purchaseOn;
    }

    public String getExpiryDates() {
        return expiryDates;
    }

    public void setExpiryDates(String expiryDates) {
        this.expiryDates = expiryDates;
    }


}
