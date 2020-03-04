
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax {

    @SerializedName("taxYear")
    @Expose
    private Integer taxYear;
    @SerializedName("taxAnnualAmount")
    @Expose
    private Integer taxAnnualAmount;
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Tax() {
    }

    /**
     * 
     * @param taxYear
     * @param taxAnnualAmount
     * @param id
     */
    public Tax(Integer taxYear, Integer taxAnnualAmount, String id) {
        super();
        this.taxYear = taxYear;
        this.taxAnnualAmount = taxAnnualAmount;
        this.id = id;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public Integer getTaxAnnualAmount() {
        return taxAnnualAmount;
    }

    public void setTaxAnnualAmount(Integer taxAnnualAmount) {
        this.taxAnnualAmount = taxAnnualAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
