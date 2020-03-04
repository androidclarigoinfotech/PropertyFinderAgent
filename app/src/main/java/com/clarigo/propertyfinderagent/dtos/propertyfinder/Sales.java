
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sales {

    @SerializedName("closeDate")
    @Expose
    private String closeDate;
    @SerializedName("office")
    @Expose
    private Office_ office;
    @SerializedName("closePrice")
    @Expose
    private Integer closePrice;
    @SerializedName("agent")
    @Expose
    private Agent_ agent;
    @SerializedName("contractDate")
    @Expose
    private Object contractDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sales() {
    }

    /**
     * 
     * @param closeDate
     * @param agent
     * @param contractDate
     * @param office
     * @param closePrice
     */
    public Sales(String closeDate, Office_ office, Integer closePrice, Agent_ agent, Object contractDate) {
        super();
        this.closeDate = closeDate;
        this.office = office;
        this.closePrice = closePrice;
        this.agent = agent;
        this.contractDate = contractDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public Office_ getOffice() {
        return office;
    }

    public void setOffice(Office_ office) {
        this.office = office;
    }

    public Integer getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Integer closePrice) {
        this.closePrice = closePrice;
    }

    public Agent_ getAgent() {
        return agent;
    }

    public void setAgent(Agent_ agent) {
        this.agent = agent;
    }

    public Object getContractDate() {
        return contractDate;
    }

    public void setContractDate(Object contractDate) {
        this.contractDate = contractDate;
    }

}
