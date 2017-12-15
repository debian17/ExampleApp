package com.example.citilin.testapp.ui.test;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class BlackBox implements Parcelable {

    private static final Object barrier = new Object();

    private int a;
    private String b;
    private static boolean isReady = false;

    void do_it() {
        synchronized (barrier) {
            Log.e("BB", "start do_it");
            while (!isReady) {
                try {
                    barrier.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //another actions
            Log.e("BB", "end do_it");
        }
    }

    void prepare() {
        synchronized (barrier) {
            Log.e("BB", "prepare");
            //prepare
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isReady = true;
        }
    }


    BlackBox(int a, String b) {
        this.a = a;
        this.b = b;
    }

    BlackBox(Parcel in) {
        a = in.readInt();
        b = in.readString();
    }

    public String getB() {
        return b;
    }

    public static final Creator<BlackBox> CREATOR = new Creator<BlackBox>() {
        @Override
        public BlackBox createFromParcel(Parcel in) {
            return new BlackBox(in);
        }

        @Override
        public BlackBox[] newArray(int size) {
            return new BlackBox[size];
        }
    };

    @Override
    public int hashCode() {
        return a * b.length();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BlackBox other = (BlackBox) obj;
        if (a != other.a) {
            return false;
        }
        if (b.hashCode() != other.b.hashCode()) {
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(a);
        parcel.writeString(b);
    }
}
