package com.radwanalbunni.automation.data.models.domain;

public class UserDataModel {

    private String id;
    private String name;
    private String email;
    private String password;

    public UserDataModel() {
    }

    public UserDataModel(UserDataModel other) {
        this.id = other.id;
        this.name = other.name;
        this.email = other.email;
        this.password = other.password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
