package com.example.citilin.testapp.ui.mychracters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Андрей on 22-Aug-17.
 */

public class MyCharacter implements Parcelable{

    private int id;
    private String name;
    private String superPower;
    private String picturePath;

    public MyCharacter(String name, String superPower, String picturePath) {
        this.name = name;
        this.superPower = superPower;
        this.picturePath = picturePath;
    }

    public MyCharacter(int id, String name, String superPower, String picturePath) {
        this.id = id;
        this.name = name;
        this.superPower = superPower;
        this.picturePath = picturePath;
    }

    private MyCharacter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        superPower = in.readString();
        picturePath = in.readString();
    }

    public static final Creator<MyCharacter> CREATOR = new Creator<MyCharacter>() {
        @Override
        public MyCharacter createFromParcel(Parcel in) {
            return new MyCharacter(in);
        }

        @Override
        public MyCharacter[] newArray(int size) {
            return new MyCharacter[size];
        }
    };

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getName() {
        return name;
    }

    public String getSuperPower() {
        return superPower;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(superPower);
        dest.writeString(picturePath);
    }
}
