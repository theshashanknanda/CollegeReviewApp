package com.project.ams.Models;

public class CollegeModel {
    public String name, location, minimumMarks, collegeEmail;

    public CollegeModel(){}

    public CollegeModel(String name, String location, String minimumMarks, String collegeEmail) {
        this.name = name;
        this.location = location;
        this.minimumMarks = minimumMarks;
        this.collegeEmail = collegeEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMinimumMarks() {
        return minimumMarks;
    }

    public void setMinimumMarks(String minimumMarks) {
        this.minimumMarks = minimumMarks;
    }

    public String getCollegeEmail() {
        return collegeEmail;
    }

    public void setCollegeEmail(String collegeEmail) {
        this.collegeEmail = collegeEmail;
    }
}
