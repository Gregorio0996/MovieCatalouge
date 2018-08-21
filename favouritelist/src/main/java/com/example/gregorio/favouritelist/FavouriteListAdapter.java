package com.example.gregorio.favouritelist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.DATE;
import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.DESCRIPTION;
import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.TITLE;
import static com.example.gregorio.favouritelist.DatabaseContract.getColumnString;

public class FavouriteListAdapter extends CursorAdapter {


    public FavouriteListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite_list, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    if(cursor != null){
        TextView tvJudul = (TextView)view.findViewById(R.id.textFilm);
        TextView tvDesc = (TextView)view.findViewById(R.id.textDesc);
        TextView tvDate = (TextView)view.findViewById(R.id.textTanggal);

        tvJudul.setText(getColumnString(cursor,TITLE));
        tvDate.setText(getColumnString(cursor,DATE));
        tvDesc.setText(getColumnString(cursor, DESCRIPTION));
    }
    }
}
