package com.dosto.models;

import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.*;

public class Borrow {
    public static final String pendingStatus = "PENDING";
    public static final String borrowedStatus = "BORROWED";
    public static final String returnedStatus = "RETURNED";


    private String creator;
    private String status;
    private List<Long> artItems = new ArrayList<>();

    public Borrow(){

    }

    public Borrow(String creator, List<Long> artItems) {
        this.creator = creator;
        this.artItems = artItems;
    }
    public Borrow(JSONObject object) {
        this.creator=(String) object.get("creator");
        JSONArray ids = (JSONArray) object.get("artItems");
        for (Object id : ids) {
            Long artItemId = (Long) id;
            artItems.add(artItemId);
        }
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Long> getArtItems() {
        return artItems;
    }

    public void setArtItems(List<Long> artItems) {
        this.artItems = artItems;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "creator='" + creator + '\'' +
                ", artItems=" + artItems.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        if (!Objects.equals(creator, borrow.creator)) return false;
        return Objects.equals(artItems, borrow.artItems);
    }

}
