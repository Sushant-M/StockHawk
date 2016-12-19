package com.sam_chordas.android.stockhawk.service;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.ui.LineGraphActivity;

/**
 * Created by sushant on 11/12/16.
 */

public class WidgetService extends RemoteViewsService {

    public String TAG = getClass().getSimpleName();

    //Projections for querying the data
    private final static String[] STOCK_COLUMNS = {
            QuoteColumns._ID,
            QuoteColumns.BIDPRICE,
            QuoteColumns.SYMBOL,
            QuoteColumns.CHANGE,
            QuoteColumns.PERCENT_CHANGE,
            QuoteColumns.ISUP
    };

    //Indices of various columns
    private final int INDEX_ID = 0;
    private final int INDEX_SYMBOL = 2;
    private final int INDEX_BIDPRICE = 1;
    private final int INDEX_PERCENT_CHANGE = 3;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {

            private Cursor mCursor = null;

            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (mCursor != null)
                    mCursor.close();
                final long identityToken = Binder.clearCallingIdentity();
                //Querying the content provider.
                mCursor = getContentResolver().query(
                        QuoteProvider.Quotes.CONTENT_URI,
                        STOCK_COLUMNS,
                        QuoteColumns.ISCURRENT + "=?",
                        new String[]{"1"},
                        null
                );
                //Dont know what this does :/
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                //Free up memory being used by the cursor.
                if (mCursor != null) {
                    mCursor.close();
                    mCursor = null;
                }
            }

            @Override
            public int getCount() {
                if(mCursor == null){
                    return 0;
                }
                return mCursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                //Check to see if there are no errors
                if (position == AdapterView.INVALID_POSITION ||
                        mCursor == null || !mCursor.moveToPosition(position)) {
                    return null;
                }

                //Gave up trying to use my own view and used the original one instead
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_quote);

                String stockSymbols, bidPrice, perChange;
                stockSymbols = mCursor.getString(INDEX_SYMBOL);
                bidPrice = mCursor.getString(INDEX_BIDPRICE);
                perChange = mCursor.getString(INDEX_PERCENT_CHANGE);
                views.setTextViewText(R.id.stock_symbol, stockSymbols);
                views.setTextViewText(R.id.bid_price, bidPrice);
                Log.d(TAG,stockSymbols);
                views.setTextViewText(R.id.change, perChange);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.list_item_quote);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (mCursor.moveToPosition(position)) {
                    return mCursor.getLong(INDEX_ID);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

}