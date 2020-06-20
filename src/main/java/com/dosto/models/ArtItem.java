package com.dosto.models;

import com.dosto.services.GlobalVars;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Objects;

public class ArtItem {
    public static final String pendingStatus = "PENDING";
    public static final String pendingBorrow = "PENDING_BORROW";
    public static final String borrowedStatus = "BORROWED";
    public static final String inGalleryStatus = "IN_GALLERY";

    private final String name;
    private final String artist;
    private final String description;
    private final String owner;
    private String originalOwner;
    private String encodedImageString;
    private Image image;
    private String status;
    private Long id;

    public ArtItem(String name, String artist, String description, String encodedImageString, String status) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.encodedImageString = encodedImageString;
        this.owner= GlobalVars.loggedUser.getUsername();
        this.status =status;
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImageString);
        this.image=new Image(new ByteArrayInputStream(decodedBytes));
    }

    public String getOriginalOwner() {
        return originalOwner;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtItem artItem = (ArtItem) o;

        if (!Objects.equals(name, artItem.name)) return false;
        if (!Objects.equals(artist, artItem.artist)) return false;
        if (!Objects.equals(description, artItem.description)) return false;
        if (!Objects.equals(owner, artItem.owner)) return false;
        return Objects.equals(encodedImageString, artItem.encodedImageString);
    }



    public String getStatus() {
        return status;
    }
    public ArtItem(JSONObject object) {
        this.name = (String)object.get("name");
        this.artist=(String)object.get("artist");
        this.description=(String)object.get("description");
        this.owner=(String)object.get("owner");
        this.encodedImageString =(String)object.get("image");
        this.status =(String) object.get("status") ;
        this.id=(Long) object.get("id");
        this.originalOwner=(String) object.get("originalOwner");
        byte[] decodedBytes = Base64.getDecoder().decode(this.encodedImageString);
        this.image=new Image(new ByteArrayInputStream(decodedBytes));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDescription() {
        return description;
    }

    public String getEncodedImageString() {
        return encodedImageString;
    }

    public Image getImage() {
        return image;
    }

    public String toString(){
        return this.name;
    }
}
