package com.example.citilin.testapp.ui.characters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Андрей on 17-Aug-17.
 */

public class DataContainer<T> {

    @SerializedName("offset")
    int offset;

    @SerializedName("limit")
    int limit;

    @SerializedName("total")
    int total;

    @SerializedName("count")
    int count;

    @SerializedName("results")
    T[] results;

    public T[] getResults(){
        return results;
    }

}
