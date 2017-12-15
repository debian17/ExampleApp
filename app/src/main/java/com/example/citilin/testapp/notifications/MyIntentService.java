package com.example.citilin.testapp.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.citilin.testapp.ui.test.BlackBox;
import com.example.citilin.testapp.ui.test.MyBroadcastReceiver;


public class MyIntentService extends IntentService {

    private static final String NAME = "MyIntentService";

    public MyIntentService() {
        super(NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyIntentService", "onCreate");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("MyIntentService", "onStart");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("MyIntentService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (intent != null) {
            BlackBox blackBox = intent.getParcelableExtra("input");
            Log.e("onHandleIntent", blackBox.getB());
            Intent resultIntent = new Intent();
            resultIntent.setAction(MyBroadcastReceiver.FROM_SERVICE);
            resultIntent.putExtra("output", blackBox);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(resultIntent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyIntentService", "onDestroy");
    }
}
