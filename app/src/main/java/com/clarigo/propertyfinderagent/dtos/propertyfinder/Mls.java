
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mls {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("daysOnMarket")
    @Expose
    private Integer daysOnMarket;
    @SerializedName("originatingSystemName")
    @Expose
    private Object originatingSystemName;
    @SerializedName("statusText")
    @Expose
    private String statusText;
    @SerializedName("areaMinor")
    @Expose
    private Object areaMinor;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Mls() {
    }

    /**
     * 
     * @param area
     * @param areaMinor
     * @param statusText
     * @param originatingSystemName
     * @param daysOnMarket
     * @param status
     */
    public Mls(String status, String area, Integer daysOnMarket, Object originatingSystemName, String statusText, Object areaMinor) {
        super();
        this.status = status;
        this.area = area;
        this.daysOnMarket = daysOnMarket;
        this.originatingSystemName = originatingSystemName;
        this.statusText = statusText;
        this.areaMinor = areaMinor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getDaysOnMarket() {
        return daysOnMarket;
    }

    public void setDaysOnMarket(Integer daysOnMarket) {
        this.daysOnMarket = daysOnMarket;
    }

    public Object getOriginatingSystemName() {
        return originatingSystemName;
    }

    public void setOriginatingSystemName(Object originatingSystemName) {
        this.originatingSystemName = originatingSystemName;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Object getAreaMinor() {
        return areaMinor;
    }

    public void setAreaMinor(Object areaMinor) {
        this.areaMinor = areaMinor;
    }

}
