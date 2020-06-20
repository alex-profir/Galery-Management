package com.dosto.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import org.json.simple.JSONObject;

import java.util.Objects;

public class User {
    private final String username;
    private final String password;
    private String role;
    private String email;
    private String lastName;
    private String firstName;

    public User(String username, String password, String role, String email, String lastName, String firstName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User (String username, String password){
        this.password=password;
        this.username=username;
    }
    public User(JSONObject object) {
        this.password = (String) object.get("password");
        this.username=(String) object.get("username");
        this.role = (String) object.get("role");
        this.firstName=(String) object.get("firstName");
        this.lastName=(String) object.get("lastName");
        this.email=(String) object.get("email");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(username, user.username)) return false;
        return Objects.equals(password, user.password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}