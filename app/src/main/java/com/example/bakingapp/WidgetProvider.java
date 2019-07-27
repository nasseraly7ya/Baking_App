package com.example.bakingapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetProvider extends IntentService {

    public WidgetProvider() {
        super("WidgetProvider");
    }

    public static void StartService(Context context){
        Intent intent = new Intent(context,WidgetProvider.class);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent( Intent intent) {
        Log.v("gothere","didit");
        if (intent!= null) {
            if (intent.getAction().equals("update")) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));
                //Now update all widgets
                NewAppWidget.UpdateData(this, appWidgetManager, appWidgetIds, intent.getStringExtra("update"));
            }
        }else {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));
            //Now update all widgets
            NewAppWidget.UpdateData(this, appWidgetManager, appWidgetIds, intent.getStringExtra("wait"));
        }
    }
}
