package com.akshayjadhav.retrofit.models;

public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private String school;

    public User(int id, String email, String password, String name, String school) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.school = school;
    }

    public User(int id, String email, String name, String school) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }
}
