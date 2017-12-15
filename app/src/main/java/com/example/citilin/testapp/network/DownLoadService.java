package com.example.citilin.testapp.network;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.test.MyBroadcastReceiver;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class DownLoadService extends Service {

    private static final String SERVICE_NAME = "downLoadService";
    public static final String DOWNLOAD_URL_KEY = "downLoadURLKey";
    private Random random;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private boolean isDownloading;
    private int notificationId;

    @Override
    public void onCreate() {
        super.onCreate();
        random = new Random(System.currentTimeMillis());
        isDownloading = false;
        notificationId = 17;
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Загрузка файла")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Загружаю файл...");
        Log.e("SERVICE", "onCreate");
    }

//    @Override
//    public void onStart(@Nullable Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Log.e("SERVICE", "onStart");
//    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("SERVICE", "onStartCommand");
        if (!isDownloading) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isDownloading = true;
                    startForeground(startId, notificationBuilder.build());
                    if (intent != null) {
                        String downLoadURL = intent.getStringExtra(DOWNLOAD_URL_KEY);

                        try {
                            URL url = new URL(downLoadURL);
                            URLConnection urlConnection = url.openConnection();

                            int lengthOfFile = urlConnection.getContentLength();
                            String extension;
                            if (downLoadURL.contains(".")) {
                                extension = downLoadURL.substring(downLoadURL.lastIndexOf("."));
                            } else {
                                extension = "bin";
                            }

                            String fileName = String.valueOf(random.nextInt()).concat(".").concat(extension);

                            BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                            FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()
                                    .getPath() + "/" + fileName);

                            byte data[] = new byte[1024];
                            long total = 0;
                            int count = 0;
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
                            Intent tempIntent = new Intent();
                            tempIntent.setAction(MyBroadcastReceiver.UPDATE_PROGRESS);
                            while ((count = inputStream.read(data)) != -1) {
                                total += count;

                                notificationBuilder.setProgress(100, (int) total * 100 / lengthOfFile, false);
                                notificationManager.notify(startId, notificationBuilder.build());

                                //tempIntent.putExtra("progress", (int) total * 100 / lengthOfFile);
                                //localBroadcastManager.sendBroadcast(tempIntent);
                                outputStream.write(data, 0, count);
                            }
                            notificationBuilder.setProgress(100, 100, false);
                            notificationManager.notify(startId, notificationBuilder.build());

                            outputStream.flush();
                            outputStream.close();
                            inputStream.close();

                            notificationBuilder.setContentText("Загрузка завершена!")
                                    .setProgress(0, 0, false);
                            notificationManager.notify(notificationId, notificationBuilder.build());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    stopSelf();
                }
            }).start();
        } else {
            Log.e("SERVICE", "УЖЕ КАЧАЮ");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SERVICE", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Log.e("SERVICE", "onHandleIntent");
//
//    }
}
