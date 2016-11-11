package com.sam_chordas.android.stockhawk.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by sushant on 11/11/16.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int appWidgetId : appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
    }
}
