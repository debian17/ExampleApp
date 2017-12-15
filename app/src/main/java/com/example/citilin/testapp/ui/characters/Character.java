package com.example.citilin.testapp.ui.characters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by citilin on 14.08.2017.
 */

public class Character {

    public static final String IMAGE_TYPE = "/portrait_uncanny";

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("thumbnail")
    public Image thumbnail;

    @SerializedName("urls")
    public Url[] urls;

    private String imagePath;

    public Character(int id, String name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        if(thumbnail == null){
            return imagePath;
        } else {
            imagePath = thumbnail.path + IMAGE_TYPE + "." + thumbnail.extension;
            return imagePath;
        }
    }

}
