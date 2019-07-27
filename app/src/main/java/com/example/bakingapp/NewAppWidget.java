package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider implements SharedPreferences.OnSharedPreferenceChangeListener {
    CharSequence ingText;

    private boolean shered;
    static Context mContext=null;
    static AppWidgetManager manager=null;
    static int id;
   static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId,String string) {

        mContext=context;
        manager=appWidgetManager;
        id=appWidgetId;


        CharSequence widgetText = context.getSharedPreferences(context.getString(R.string.appwidget_text),Context.MODE_PRIVATE).getString(context.getString(R.string.appwidget_text),"no Vslue");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.appwidget_text, string);
            // Instruct the widget manager to update the widget
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(appWidgetId, pendingIntent);

            Intent serviceIntent = new Intent(context,WidgetProvider.class);
            PendingIntent pendingService = PendingIntent.getService(context,0,serviceIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(appWidgetId,pendingService);

            appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them


        WidgetProvider.StartService(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("Prfances","changed");
        shered = true;

    }


    public static void UpdateData(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String ingedient) {
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId,ingedient);
            }




    }

}

