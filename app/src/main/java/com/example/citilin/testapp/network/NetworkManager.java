package com.example.citilin.testapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.citilin.testapp.App;

public class NetworkManager {
    public static boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }
        else {
            return false;
        }
    }
}
