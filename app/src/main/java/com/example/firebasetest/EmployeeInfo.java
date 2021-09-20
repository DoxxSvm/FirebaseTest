package com.example.firebasetest;

public class EmployeeInfo {

    // string variable for
    // storing employee name.
    private String startUpName;

    // string variable for storing
    // employee contact number
    private String potential;

    // string variable for storing
    // employee address.
    private String buildTime;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public EmployeeInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getstartUpName() {
        return startUpName;
    }

    public void setstartUpName(String startUpName) {
        this.startUpName = startUpName;
    }

    public String getpotential() {
        return potential;
    }

    public void setpotential(String potential) {
        this.potential = potential;
    }

    public String getbuildTime() {
        return buildTime;
    }

    public void setbuildTime(String buildTime) {
        this.buildTime = buildTime;
    }
}

