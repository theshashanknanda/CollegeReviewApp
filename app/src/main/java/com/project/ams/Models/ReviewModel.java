package com.project.ams.Models;

public class ReviewModel {

    public String review;
    public String userName;
    public String rating;

    public ReviewModel(){}

    public ReviewModel(String review, String userName, String rating) {
        this.review = review;
        this.userName = userName;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
