package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MyAsyncTaskLoad extends AsyncTaskLoader<ArrayList<FilmItems>> {
    private ArrayList<FilmItems> mData;
    private boolean mHasResult = false;

    private String mKumpulanFilm;

    public MyAsyncTaskLoad(final Context context, String kumpulanFilm) {
        super(context);

        onContentChanged();
        this.mKumpulanFilm = kumpulanFilm;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<FilmItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }


    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<FilmItems> filmItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.MOVIE_DB_API_KEY + "&language=en-US&query=" + mKumpulanFilm;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String hasil = new String(responseBody);
                    JSONObject responseObject = new JSONObject(hasil);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject film = results.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(film);
                        filmItemses.add(filmItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return filmItemses;
    }

    protected void onReleaseResources(ArrayList<FilmItems> mData) {
        //nothing to do.
    }

}
