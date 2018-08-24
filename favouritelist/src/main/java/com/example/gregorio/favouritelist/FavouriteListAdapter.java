package com.example.gregorio.favouritelist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.DATE;
import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.DESCRIPTION;
import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.POSTER;
import static com.example.gregorio.favouritelist.DatabaseContract.FavouriteColumns.TITLE;
import static com.example.gregorio.favouritelist.DatabaseContract.getColumnString;

public class FavouriteListAdapter extends CursorAdapter {
    private ArrayList<FilmItemss> filmItemsses = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private String final_overview;



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
        if (cursor != null) {
            TextView tvJudul = (TextView) view.findViewById(R.id.textFilm);
            TextView tvDesc = (TextView) view.findViewById(R.id.textDesc);
            TextView tvDate = (TextView) view.findViewById(R.id.textTanggal);
            ImageView imgPoster = (ImageView) view.findViewById(R.id.img_poster);

            tvJudul.setText(getColumnString(cursor, TITLE));

            final String tanggalrilis = getColumnString(cursor, DATE);
            tvDesc.setText(getColumnString(cursor, DESCRIPTION));
            Glide.with(context).load("http://image.tmdb.org/t/p/w185/"+getColumnString(cursor, POSTER)).into(imgPoster);

            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            try

            {
                Date date = date_format.parse(tanggalrilis);

                SimpleDateFormat new_date = new SimpleDateFormat("EEEE, dd/MM/yyyy");
                String date_rilis = new_date.format(date);
                tvDate.setText(date_rilis);
            } catch (
                    ParseException e)

            {
                e.printStackTrace();
            }


        }
    }

}
