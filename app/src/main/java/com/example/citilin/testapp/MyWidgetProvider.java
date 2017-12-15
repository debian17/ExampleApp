package com.example.citilin.testapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.citilin.testapp.ui.mychracters.addmycharacter.AddMyCharactersActivity;

public class MyWidgetProvider extends AppWidgetProvider {

    private static final String SHOW_TOAST = "showToast";
    private static final String ADD_MY_CHARACTER = "addMyCharacter";

    private static final String TAG_MESSAGE = "tagMessage";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e("W", "onUpdate");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        Intent showToastIntent = new Intent(context, MyWidgetProvider.class);
        showToastIntent.setAction(SHOW_TOAST);
        showToastIntent.putExtra(TAG_MESSAGE, "Toast message!");

        Intent addMyCharacterIntent = new Intent(context, MyWidgetProvider.class);
        addMyCharacterIntent.setAction(ADD_MY_CHARACTER);

        PendingIntent showToastPI = PendingIntent.getBroadcast(context, 0, showToastIntent, 0);
        PendingIntent addMyCharacterPI = PendingIntent.getBroadcast(context, 0, addMyCharacterIntent, 0);

        remoteViews.setOnClickFillInIntent(R.id.widgetBTN1, showToastIntent);

        remoteViews.setOnClickPendingIntent(R.id.widgetBTN1, showToastPI);
        remoteViews.setOnClickPendingIntent(R.id.widgetBTN2, addMyCharacterPI);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.e("ACTION", action);
        switch (action) {
            case SHOW_TOAST: {
                try {
                    String msg = intent.getStringExtra(TAG_MESSAGE);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("Error", "msg = null");
                }
                break;
            }
            case ADD_MY_CHARACTER: {
                Intent addMCIntent = new Intent(context, AddMyCharactersActivity.class);
                context.startActivity(addMCIntent);
                break;
            }
        }
        super.onReceive(context, intent);
    }
}
