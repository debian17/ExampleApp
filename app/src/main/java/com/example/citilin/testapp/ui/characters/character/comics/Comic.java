package com.example.citilin.testapp.ui.characters.character.comics;

        import com.example.citilin.testapp.ui.characters.Image;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Андрей on 16-Aug-17.
 */

public class Comic {

    private static final String IMAGE_TYPE = "/portrait_uncanny";

    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("format")
    String format;

    @SerializedName("pageCount")
    int pageCount;

    @SerializedName("thumbnail")
    Image thumbnail;

    public String getImagePath() {
        if(thumbnail == null){
            return null;
        }
        return thumbnail.path + IMAGE_TYPE + "." + thumbnail.extension;
    }

}
