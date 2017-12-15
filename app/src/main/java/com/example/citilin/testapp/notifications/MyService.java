package com.example.citilin.testapp.notifications;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {

    private IBinder iBinder;

    private Thread thread;

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public int getInt(int a) {
        Log.d("SERVICE", String.valueOf(a));
        return a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iBinder = new LocalBinder();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 60; i++) {
                    if (i == 30) {
                        stopSelf();
                    }
                    Log.e("SERVICE", String.valueOf(i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Log.d("SERVICE", "onCreate");

//        Intent notificationIntent = new Intent(this, MenuActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("My Awesome App")
//                .setContentText("Doing some work...")
//                .setContentIntent(pendingIntent).build();

        //startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "onStartCommand");
        Log.d("onStartCommand", intent.getStringExtra("key"));


                //thread.start();
//
//        if (timerTask != null) {
//            timerTask.cancel();
//        }
//
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        };
//
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 200; i++) {
//                    Log.e("TIME", String.valueOf(i));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//
//        timer.schedule(timerTask, 0);

        //stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //thread.interrupt();
        Log.d("SERVICE", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("SERVICE", "onBind");
        Log.e("SERVICE", intent.getStringExtra("key"));
        return iBinder;
    }
}
