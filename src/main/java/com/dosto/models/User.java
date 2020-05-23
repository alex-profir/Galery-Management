package com.dosto.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import org.json.simple.JSONObject;

import java.util.Objects;

public class User {
    private final String username;
    private final String password;
    private String role;

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
        this.password= (String) object.get("password");
        this.username=(String) object.get("username");
        this.role = (String) object.get("role");
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