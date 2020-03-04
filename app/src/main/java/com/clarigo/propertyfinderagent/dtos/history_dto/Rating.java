package com.clarigo.propertyfinderagent.dtos.history_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

@SerializedName("review")
@Expose
private String review;
@SerializedName("rating")
@Expose
private String rating;

/**
* No args constructor for use in serialization
*
*/
public Rating() {
}

/**
*
* @param review
* @param rating
*/
public Rating(String review, String rating) {
super();
this.review = review;
this.rating = rating;
}

public String getReview() {
return review;
}

public void setReview(String review) {
this.review = review;
}

public String getRating() {
return rating;
}

public void setRating(String rating) {
this.rating = rating;
}

}