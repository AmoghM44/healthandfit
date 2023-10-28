package com.example.healthandfit;

public class UserInfo {

    // string variable for
    // storing employee name.
    private String name;

    // string variable for storing
    // employee contact number
    private String age;


    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public UserInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


}

