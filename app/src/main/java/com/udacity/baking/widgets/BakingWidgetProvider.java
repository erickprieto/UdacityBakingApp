package com.udacity.baking.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.udacity.baking.BakingApplication;
import com.udacity.baking.R;
import com.udacity.baking.activities.RecipesListActivity;

/**
 *
 * @author Erick Prieto
 * @since 2018
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widgetprovider);
        SharedPreferences sharedPreferences = BakingApplication.getSharedPreferences();
        remoteViews.setTextViewText(R.id.widgetProvider_titleTextView
                , sharedPreferences.getString(BakingApplication.PREFERENCES_WIDGETPROVIDER_TITLE, ""));
        remoteViews.setTextViewText(R.id.widgetProvider_ingredientsTextView
                , sharedPreferences.getString(BakingApplication.PREFERENCES_WIDGETPROVIDER_CONTENT, ""));

        Intent intent = new Intent(context, RecipesListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widgetProvider_ingredientsTextView, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.widgetProvider_titleTextView, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Update all active widgets
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
