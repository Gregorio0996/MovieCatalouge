package com.example.gregorio.moviecatalouge;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.gregorio.moviecatalouge.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mcontext;
    private int mAppWidgetId;
    private Cursor list;
    FavouriteHelper favouriteHelper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mcontext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {
        list = mcontext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        ResItems items = getItem(i);
        RemoteViews rv = new RemoteViews(mcontext.getPackageName(), R.layout.widget_item);
        Bitmap bitmap = null;
        String judul = "";
        try {

            bitmap = Glide.with(mcontext)
                    .load("http://image.tmdb.org/t/p/w185" + items.getPoster())
                    .asBitmap()
                    .error(new ColorDrawable(mcontext.getResources().getColor(R.color.colorPrimary)))
                    .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setImageViewBitmap(R.id.imageView, bitmap);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    private ResItems getItem(int position){
        if(!list.moveToPosition(position)){
            throw new IllegalStateException("position invalid");
        }
        return new ResItems(list);
    }
}
