package com.example.citilin.testapp.notifications;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.MenuActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    private static String TITLE = "Marvel Hero Custom Title";
    private static String KEY_REPLY = "keyReply";
    private static String MESSAGE_ACTION = "messageAction";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String messageBody) {
        Log.d("BODY", messageBody);

        Intent activityIntent = new Intent(getApplicationContext(), MenuActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activityIntent.putExtra("key", messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, activityIntent, 0);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.customTitle, TITLE);
        remoteViews.setTextViewText(R.id.customContent, messageBody);
        //remoteViews.setOnClickPendingIntent(R.id.notificationBTN, pendingIntent);


//        String replyLabel = "Ответить";
//        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
//                .setLabel(replyLabel)
//                .build();
//
//        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//        intent.setAction(MESSAGE_ACTION);
//        intent.putExtra("key", messageBody);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent1 = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//
//        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.ic_check_mark,
//                replyLabel, pendingIntent1)
//                .addRemoteInput(remoteInput)
//                .setAllowGeneratedReplies(true)
//                .build();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle(TITLE)
                .setContentText(messageBody)
                .setContent(remoteViews)
                //.addAction(replyAction)
                .setDefaults(Notification.DEFAULT_ALL)
                .setCustomHeadsUpContentView(remoteViews)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        inboxStyle.setBigContentTitle("BIG CONTENT TITLE");
//        for (int i = 0; i < 4; i++) {
//            inboxStyle.addLine("line number" + String.valueOf(i));
//        }
        //notificationBuilder.setStyle(inboxStyle);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
