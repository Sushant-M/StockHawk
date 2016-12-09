package com.sam_chordas.android.stockhawk.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.rest.QuoteCursorAdapter;

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
            QuoteCursorAdapter adapter = new QuoteCursorAdapter(context,null);

            //Code to open the activity when opened
            RemoteViews remoteView = new RemoteViews(context.getPackageName(),R.id.widget_list);
            Intent intent = new Intent(context,MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            remoteView.setOnClickPendingIntent(R.id.WidgetList,pendingIntent);


        }
    }
}
