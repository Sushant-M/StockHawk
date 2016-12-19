package com.sam_chordas.android.stockhawk.ui;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.rest.QuoteCursorAdapter;
import com.sam_chordas.android.stockhawk.service.StockIntentService;
import com.sam_chordas.android.stockhawk.service.WidgetService;

/**
 * Created by sushant on 11/11/16.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    final String APPWIDGETID = "WidgetID";
    final String TAG = getClass().getSimpleName();
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context, views);
                Log.d(TAG,"New adapter used");
            } else {
                setRemoteAdapterV11(appWidgetId, context, views);
                Log.d(TAG,"Old adapter used");
            }
            Log.d(TAG,"Supposedly set the adapter");

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @SuppressWarnings("deprecation")
    public void setRemoteAdapterV11(int widgetID, Context context, RemoteViews views) {
        views.setRemoteAdapter(widgetID,R.id.widgetList,new Intent(context,WidgetService.class));
    }

    public void setRemoteAdapter(Context context, final @NonNull  RemoteViews views){
        views.setRemoteAdapter(R.id.widgetList,new Intent(context,WidgetService.class));
    }




    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        Log.i("Receiver", "Broadcast received: " + action);
        if(action.equals(MyStocksActivity.ACTION_DATA_UPDATED)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetList);
        }
    }
}
