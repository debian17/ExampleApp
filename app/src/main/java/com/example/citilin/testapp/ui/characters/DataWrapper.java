package com.example.citilin.testapp.ui.characters;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Андрей on 17-Aug-17.
 */

public class DataWrapper<T> {

    @SerializedName("code")
    public int code;

    @SerializedName("status")
    public String status;

    @SerializedName("data")
    public DataContainer<T> data;

}
