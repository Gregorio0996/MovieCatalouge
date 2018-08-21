package com.example.gregorio.favouritelist;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.gregorio.favouritelist.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView lvFav;
    private final int LOAD_FAV_ID = 101;
    String[] movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFav = (ListView)findViewById(R.id.lv_fav);
        getSupportLoaderManager().initLoader(LOAD_FAV_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    if (data.moveToFirst()){
        movies = new String[data.getCount()];
        for (int i=0; i < data.getCount(); i++){
            data.moveToPosition(i);
            movies[i] = data.getString(3);
        };
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(movies));
        lvFav.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList));
    }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
