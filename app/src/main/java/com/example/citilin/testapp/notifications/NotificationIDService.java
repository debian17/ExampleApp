package com.example.citilin.testapp.notifications;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class NotificationIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.e("Not", "Token [" + tkn + "]");
    }
}
