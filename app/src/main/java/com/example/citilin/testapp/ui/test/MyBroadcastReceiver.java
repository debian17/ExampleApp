package com.example.citilin.testapp.ui.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public static final String MY_ACTION = "myAction";
    public static final String MESSAGE = "stringKey";
    public static final String FROM_SERVICE = "fromService";
    public static final String UPDATE_PROGRESS = "updateProgress";

    private String action;

    public interface Receive {
        void myAction(String msg);

        void updateProgress(Intent intent);
    }

    private Receive receive;

    public MyBroadcastReceiver(Receive receive) {
        this.receive = receive;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MyBroadcastReceiver", "onReceive");
        action = intent.getAction();
        switch (action) {
            case MY_ACTION: {
                receive.myAction(intent.getStringExtra(MESSAGE));
                break;
            }
            case FROM_SERVICE: {
                BlackBox blackBox = intent.getParcelableExtra("output");
                Log.e("CATCH", blackBox.getB());
                break;
            }
            case UPDATE_PROGRESS: {
                receive.updateProgress(intent);
                break;
            }
        }
    }
}
