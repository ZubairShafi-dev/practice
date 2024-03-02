package com.codingempires.practice;

public class UserModel {
    private String name;
    private String password;
    private String email;
    String id;

    // Constructor
    public UserModel(String name, String password, String email,String id) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}