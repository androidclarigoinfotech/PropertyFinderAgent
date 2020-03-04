
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PROPERTY_MAIN_DTO {

    @SerializedName("privateRemarks")
    @Expose
    private String privateRemarks;
    @SerializedName("showingContactName")
    @Expose
    private Object showingContactName;
    @SerializedName("property")
    @Expose
    private Property property;
    @SerializedName("mlsId")
    @Expose
    private Integer mlsId;
    @SerializedName("showingContactPhone")
    @Expose
    private Object showingContactPhone;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("showingInstructions")
    @Expose
    private String showingInstructions;
    @SerializedName("office")
    @Expose
    private Office office;
    @SerializedName("leaseTerm")
    @Expose
    private Object leaseTerm;
    @SerializedName("disclaimer")
    @Expose
    private String disclaimer;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("agreement")
    @Expose
    private String agreement;
    @SerializedName("listDate")
    @Expose
    private String listDate;
    @SerializedName("agent")
    @Expose
    private Agent agent;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("school")
    @Expose
    private School school;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("listPrice")
    @Expose
    private Integer listPrice;
    @SerializedName("internetAddressDisplay")
    @Expose
    private Object internetAddressDisplay;
    @SerializedName("listingId")
    @Expose
    private String listingId;
    @SerializedName("mls")
    @Expose
    private Mls mls;
    @SerializedName("internetEntireListingDisplay")
    @Expose
    private Object internetEntireListingDisplay;
    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("tax")
    @Expose
    private Tax tax;
    @SerializedName("coAgent")
    @Expose
    private CoAgent coAgent;
    @SerializedName("sales")
    @Expose
    private Sales sales;
    @SerializedName("leaseType")
    @Expose
    private String leaseType;
    @SerializedName("virtualTourUrl")
    @Expose
    private Object virtualTourUrl;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("association")
    @Expose
    private Association association;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PROPERTY_MAIN_DTO() {
    }

    /**
     * 
     * @param leaseTerm
     * @param listDate
     * @param agent
     * @param association
     * @param office
     * @param internetAddressDisplay
     * @param photos
     * @param coAgent
     * @param sales
     * @param geo
     * @param leaseType
     * @param terms
     * @param school
     * @param property
     * @param modified
     * @param showingContactName
     * @param mlsId
     * @param disclaimer
     * @param virtualTourUrl
     * @param address
     * @param agreement
     * @param tax
     * @param privateRemarks
     * @param listingId
     * @param mls
     * @param showingContactPhone
     * @param listPrice
     * @param remarks
     * @param showingInstructions
     * @param internetEntireListingDisplay
     */
    public PROPERTY_MAIN_DTO(String privateRemarks, Object showingContactName, Property property, Integer mlsId, Object showingContactPhone, String terms, String showingInstructions, Office office, Object leaseTerm, String disclaimer, Address address, String agreement, String listDate, Agent agent, String modified, School school, List<String> photos, Integer listPrice, Object internetAddressDisplay, String listingId, Mls mls, Object internetEntireListingDisplay, Geo geo, Tax tax, CoAgent coAgent, Sales sales, String leaseType, Object virtualTourUrl, String remarks, Association association) {
        super();
        this.privateRemarks = privateRemarks;
        this.showingContactName = showingContactName;
        this.property = property;
        this.mlsId = mlsId;
        this.showingContactPhone = showingContactPhone;
        this.terms = terms;
        this.showingInstructions = showingInstructions;
        this.office = office;
        this.leaseTerm = leaseTerm;
        this.disclaimer = disclaimer;
        this.address = address;
        this.agreement = agreement;
        this.listDate = listDate;
        this.agent = agent;
        this.modified = modified;
        this.school = school;
        this.photos = photos;
        this.listPrice = listPrice;
        this.internetAddressDisplay = internetAddressDisplay;
        this.listingId = listingId;
        this.mls = mls;
        this.internetEntireListingDisplay = internetEntireListingDisplay;
        this.geo = geo;
        this.tax = tax;
        this.coAgent = coAgent;
        this.sales = sales;
        this.leaseType = leaseType;
        this.virtualTourUrl = virtualTourUrl;
        this.remarks = remarks;
        this.association = association;
    }

    public String getPrivateRemarks() {
        return privateRemarks;
    }

    public void setPrivateRemarks(String privateRemarks) {
        this.privateRemarks = privateRemarks;
    }

    public Object getShowingContactName() {
        return showingContactName;
    }

    public void setShowingContactName(Object showingContactName) {
        this.showingContactName = showingContactName;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Integer getMlsId() {
        return mlsId;
    }

    public void setMlsId(Integer mlsId) {
        this.mlsId = mlsId;
    }

    public Object getShowingContactPhone() {
        return showingContactPhone;
    }

    public void setShowingContactPhone(Object showingContactPhone) {
        this.showingContactPhone = showingContactPhone;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getShowingInstructions() {
        return showingInstructions;
    }

    public void setShowingInstructions(String showingInstructions) {
        this.showingInstructions = showingInstructions;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Object getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(Object leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Object getInternetAddressDisplay() {
        return internetAddressDisplay;
    }

    public void setInternetAddressDisplay(Object internetAddressDisplay) {
        this.internetAddressDisplay = internetAddressDisplay;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public Mls getMls() {
        return mls;
    }

    public void setMls(Mls mls) {
        this.mls = mls;
    }

    public Object getInternetEntireListingDisplay() {
        return internetEntireListingDisplay;
    }

    public void setInternetEntireListingDisplay(Object internetEntireListingDisplay) {
        this.internetEntireListingDisplay = internetEntireListingDisplay;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public CoAgent getCoAgent() {
        return coAgent;
    }

    public void setCoAgent(CoAgent coAgent) {
        this.coAgent = coAgent;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    public Object getVirtualTourUrl() {
        return virtualTourUrl;
    }

    public void setVirtualTourUrl(Object virtualTourUrl) {
        this.virtualTourUrl = virtualTourUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

}
