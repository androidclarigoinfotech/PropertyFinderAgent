
package com.clarigo.propertyfinderagent.dtos.propertyfinder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Property {

    @SerializedName("roof")
    @Expose
    private String roof;
    @SerializedName("cooling")
    @Expose
    private Object cooling;
    @SerializedName("style")
    @Expose
    private String style;
    @SerializedName("area")
    @Expose
    private Integer area;
    @SerializedName("bathsFull")
    @Expose
    private Integer bathsFull;
    @SerializedName("bathsHalf")
    @Expose
    private Integer bathsHalf;
    @SerializedName("stories")
    @Expose
    private Integer stories;
    @SerializedName("fireplaces")
    @Expose
    private Integer fireplaces;
    @SerializedName("flooring")
    @Expose
    private Object flooring;
    @SerializedName("heating")
    @Expose
    private String heating;
    @SerializedName("bathrooms")
    @Expose
    private Object bathrooms;
    @SerializedName("foundation")
    @Expose
    private String foundation;
    @SerializedName("laundryFeatures")
    @Expose
    private String laundryFeatures;
    @SerializedName("occupantName")
    @Expose
    private Object occupantName;
    @SerializedName("ownerName")
    @Expose
    private Object ownerName;
    @SerializedName("lotDescription")
    @Expose
    private String lotDescription;
    @SerializedName("pool")
    @Expose
    private String pool;
    @SerializedName("subType")
    @Expose
    private Object subType;
    @SerializedName("bedrooms")
    @Expose
    private Integer bedrooms;
    @SerializedName("interiorFeatures")
    @Expose
    private String interiorFeatures;
    @SerializedName("lotSize")
    @Expose
    private String lotSize;
    @SerializedName("areaSource")
    @Expose
    private String areaSource;
    @SerializedName("maintenanceExpense")
    @Expose
    private Object maintenanceExpense;
    @SerializedName("additionalRooms")
    @Expose
    private String additionalRooms;
    @SerializedName("exteriorFeatures")
    @Expose
    private String exteriorFeatures;
    @SerializedName("water")
    @Expose
    private Object water;
    @SerializedName("view")
    @Expose
    private String view;
    @SerializedName("lotSizeArea")
    @Expose
    private Object lotSizeArea;
    @SerializedName("subdivision")
    @Expose
    private String subdivision;
    @SerializedName("construction")
    @Expose
    private String construction;
    @SerializedName("parking")
    @Expose
    private Parking parking;
    @SerializedName("lotSizeAreaUnits")
    @Expose
    private Object lotSizeAreaUnits;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("garageSpaces")
    @Expose
    private Double garageSpaces;
    @SerializedName("bathsThreeQuarter")
    @Expose
    private Object bathsThreeQuarter;
    @SerializedName("accessibility")
    @Expose
    private String accessibility;
    @SerializedName("acres")
    @Expose
    private Object acres;
    @SerializedName("occupantType")
    @Expose
    private Object occupantType;
    @SerializedName("subTypeText")
    @Expose
    private Object subTypeText;
    @SerializedName("yearBuilt")
    @Expose
    private Integer yearBuilt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Property() {
    }

    /**
     * 
     * @param parking
     * @param laundryFeatures
     * @param flooring
     * @param interiorFeatures
     * @param exteriorFeatures
     * @param accessibility
     * @param maintenanceExpense
     * @param bathsFull
     * @param occupantName
     * @param foundation
     * @param type
     * @param lotSizeAreaUnits
     * @param yearBuilt
     * @param subdivision
     * @param view
     * @param ownerName
     * @param lotDescription
     * @param bathsThreeQuarter
     * @param garageSpaces
     * @param construction
     * @param heating
     * @param areaSource
     * @param area
     * @param roof
     * @param stories
     * @param lotSize
     * @param pool
     * @param fireplaces
     * @param bathrooms
     * @param water
     * @param subTypeText
     * @param additionalRooms
     * @param bedrooms
     * @param occupantType
     * @param bathsHalf
     * @param cooling
     * @param style
     * @param subType
     * @param acres
     * @param lotSizeArea
     */
    public Property(String roof, Object cooling, String style, Integer area, Integer bathsFull, Integer bathsHalf, Integer stories, Integer fireplaces, Object flooring, String heating, Object bathrooms, String foundation, String laundryFeatures, Object occupantName, Object ownerName, String lotDescription, String pool, Object subType, Integer bedrooms, String interiorFeatures, String lotSize, String areaSource, Object maintenanceExpense, String additionalRooms, String exteriorFeatures, Object water, String view, Object lotSizeArea, String subdivision, String construction, Parking parking, Object lotSizeAreaUnits, String type, Double garageSpaces, Object bathsThreeQuarter, String accessibility, Object acres, Object occupantType, Object subTypeText, Integer yearBuilt) {
        super();
        this.roof = roof;
        this.cooling = cooling;
        this.style = style;
        this.area = area;
        this.bathsFull = bathsFull;
        this.bathsHalf = bathsHalf;
        this.stories = stories;
        this.fireplaces = fireplaces;
        this.flooring = flooring;
        this.heating = heating;
        this.bathrooms = bathrooms;
        this.foundation = foundation;
        this.laundryFeatures = laundryFeatures;
        this.occupantName = occupantName;
        this.ownerName = ownerName;
        this.lotDescription = lotDescription;
        this.pool = pool;
        this.subType = subType;
        this.bedrooms = bedrooms;
        this.interiorFeatures = interiorFeatures;
        this.lotSize = lotSize;
        this.areaSource = areaSource;
        this.maintenanceExpense = maintenanceExpense;
        this.additionalRooms = additionalRooms;
        this.exteriorFeatures = exteriorFeatures;
        this.water = water;
        this.view = view;
        this.lotSizeArea = lotSizeArea;
        this.subdivision = subdivision;
        this.construction = construction;
        this.parking = parking;
        this.lotSizeAreaUnits = lotSizeAreaUnits;
        this.type = type;
        this.garageSpaces = garageSpaces;
        this.bathsThreeQuarter = bathsThreeQuarter;
        this.accessibility = accessibility;
        this.acres = acres;
        this.occupantType = occupantType;
        this.subTypeText = subTypeText;
        this.yearBuilt = yearBuilt;
    }

    public String getRoof() {
        return roof;
    }

    public void setRoof(String roof) {
        this.roof = roof;
    }

    public Object getCooling() {
        return cooling;
    }

    public void setCooling(Object cooling) {
        this.cooling = cooling;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getBathsFull() {
        return bathsFull;
    }

    public void setBathsFull(Integer bathsFull) {
        this.bathsFull = bathsFull;
    }

    public Integer getBathsHalf() {
        return bathsHalf;
    }

    public void setBathsHalf(Integer bathsHalf) {
        this.bathsHalf = bathsHalf;
    }

    public Integer getStories() {
        return stories;
    }

    public void setStories(Integer stories) {
        this.stories = stories;
    }

    public Integer getFireplaces() {
        return fireplaces;
    }

    public void setFireplaces(Integer fireplaces) {
        this.fireplaces = fireplaces;
    }

    public Object getFlooring() {
        return flooring;
    }

    public void setFlooring(Object flooring) {
        this.flooring = flooring;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public Object getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Object bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getLaundryFeatures() {
        return laundryFeatures;
    }

    public void setLaundryFeatures(String laundryFeatures) {
        this.laundryFeatures = laundryFeatures;
    }

    public Object getOccupantName() {
        return occupantName;
    }

    public void setOccupantName(Object occupantName) {
        this.occupantName = occupantName;
    }

    public Object getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(Object ownerName) {
        this.ownerName = ownerName;
    }

    public String getLotDescription() {
        return lotDescription;
    }

    public void setLotDescription(String lotDescription) {
        this.lotDescription = lotDescription;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public Object getSubType() {
        return subType;
    }

    public void setSubType(Object subType) {
        this.subType = subType;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getInteriorFeatures() {
        return interiorFeatures;
    }

    public void setInteriorFeatures(String interiorFeatures) {
        this.interiorFeatures = interiorFeatures;
    }

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    public String getAreaSource() {
        return areaSource;
    }

    public void setAreaSource(String areaSource) {
        this.areaSource = areaSource;
    }

    public Object getMaintenanceExpense() {
        return maintenanceExpense;
    }

    public void setMaintenanceExpense(Object maintenanceExpense) {
        this.maintenanceExpense = maintenanceExpense;
    }

    public String getAdditionalRooms() {
        return additionalRooms;
    }

    public void setAdditionalRooms(String additionalRooms) {
        this.additionalRooms = additionalRooms;
    }

    public String getExteriorFeatures() {
        return exteriorFeatures;
    }

    public void setExteriorFeatures(String exteriorFeatures) {
        this.exteriorFeatures = exteriorFeatures;
    }

    public Object getWater() {
        return water;
    }

    public void setWater(Object water) {
        this.water = water;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Object getLotSizeArea() {
        return lotSizeArea;
    }

    public void setLotSizeArea(Object lotSizeArea) {
        this.lotSizeArea = lotSizeArea;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public Object getLotSizeAreaUnits() {
        return lotSizeAreaUnits;
    }

    public void setLotSizeAreaUnits(Object lotSizeAreaUnits) {
        this.lotSizeAreaUnits = lotSizeAreaUnits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getGarageSpaces() {
        return garageSpaces;
    }

    public void setGarageSpaces(Double garageSpaces) {
        this.garageSpaces = garageSpaces;
    }

    public Object getBathsThreeQuarter() {
        return bathsThreeQuarter;
    }

    public void setBathsThreeQuarter(Object bathsThreeQuarter) {
        this.bathsThreeQuarter = bathsThreeQuarter;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public Object getAcres() {
        return acres;
    }

    public void setAcres(Object acres) {
        this.acres = acres;
    }

    public Object getOccupantType() {
        return occupantType;
    }

    public void setOccupantType(Object occupantType) {
        this.occupantType = occupantType;
    }

    public Object getSubTypeText() {
        return subTypeText;
    }

    public void setSubTypeText(Object subTypeText) {
        this.subTypeText = subTypeText;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

}
