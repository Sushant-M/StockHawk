package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteDatabase;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

import static com.sam_chordas.android.stockhawk.data.QuoteProvider.AUTHORITY;

/**
 * Created by sushant on 11/12/16.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(),intent);
    }
    class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private Cursor mCursor;
        private int mAppWidgetId;
        private ArrayList<String> List = new ArrayList();

        public StackRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            populateList();
        }

        private void populateList() {
            int index = mCursor.getColumnIndex("symbol");
            int val = mCursor.getColumnIndex("bid_price");
            Uri uri = Uri.parse("content://" + AUTHORITY);
            String projection[] =
                            {QuoteColumns._ID,
                            QuoteColumns.BIDPRICE,
                            QuoteColumns.CHANGE};
            mCursor = mContext.getContentResolver().query(uri, null, null, null, null);

            while(mCursor.moveToNext()){
                String stock_name = mCursor.getString(index);
                String stock_value = mCursor.getString(val);
                List.add(stock_name + "   " +stock_value);
            }
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (mCursor != null) {
                mCursor.close();
            }

            Uri uri = Uri.parse("content://" + AUTHORITY);
            String projection[] =
                    {QuoteColumns._ID,
                    QuoteColumns.BIDPRICE,
                    QuoteColumns.CHANGE};
                     mCursor = mContext.getContentResolver().query(uri, null, null,
                    null, null);
        }

        @Override
        public void onDestroy() {
            if (mCursor != null) {
                mCursor.close();
            }
        }

        @Override
        public int getCount() {
            return List.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            final RemoteViews remoteView = new RemoteViews(
                    mContext.getPackageName(), R.layout.widget_layout);
            String value = List.get(position);
            remoteView.setTextViewText(R.id.content, value);
            Log.d("YOLO",value);
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}


