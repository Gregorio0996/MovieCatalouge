package com.example.gregorio.moviecatalouge;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NowPAsyncTaskLoad extends AsyncTaskLoader<ArrayList<FilmItems>> {

    private ArrayList<FilmItems> mFilmItem;
    private boolean mHasResult = false;

    public NowPAsyncTaskLoad(final Context context, ArrayList<FilmItems> mFilmItem) {
        super(context);
        onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mFilmItem);
    }

    @Override
    public void deliverResult(final ArrayList<FilmItems> data) {
        mFilmItem = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if (mHasResult) {
            onReleaseResource(mFilmItem);
            mFilmItem = null;
            mHasResult = false;
        }
    }

    private void onReleaseResource(ArrayList<FilmItems> mFilmItem) {

    }


    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<FilmItems> filmItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.MOVIE_DB_API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String hasil = new String(responseBody);
                    JSONObject responseObject = new JSONObject(hasil);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(film);
                        filmItemses.add(filmItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return filmItemses;
    }
}
