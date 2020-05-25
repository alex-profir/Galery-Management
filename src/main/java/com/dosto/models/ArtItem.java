package com.dosto.models;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.Base64;

public class ArtItem {
    private final String name;
    private final String artist;
    private final String description;
    private String encodedImage;
    private Image image;
    public ArtItem(String name, String artist, String description,String encodedImage) {
        this.name = name;
        this.artist = artist;
        this.description = description;
        this.encodedImage=encodedImage;
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage);
        this.image=new Image(new ByteArrayInputStream(decodedBytes));
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

    public String getEncodedImage() {
        return encodedImage;
    }

    public Image getImage() {
        return image;
    }
}
