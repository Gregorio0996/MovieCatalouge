package com.example.gregorio.moviecatalouge;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import static com.example.gregorio.moviecatalouge.DatabaseContract.AUTHORITY;
import static com.example.gregorio.moviecatalouge.DatabaseContract.CONTENT_URI;
import static com.example.gregorio.moviecatalouge.DatabaseContract.getColumnInt;

public class FavouriteProvider extends ContentProvider {
    public static final int FAVOURITE = 1;
    public static final int FAVOURITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME, FAVOURITE);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_NAME + "/#", FAVOURITE_ID);
    }

    private FavouriteHelper favouriteHelper;


    @Override
    public boolean onCreate() {
        favouriteHelper = new FavouriteHelper(getContext());
        favouriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                cursor = favouriteHelper.queryProvider();
                break;
            case FAVOURITE_ID:
                cursor = favouriteHelper.queryByIDProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                added = favouriteHelper.insertProvider(contentValues);
                break;

            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE_ID:
                deleted = favouriteHelper.deleteProvider(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE_ID:
                updated = favouriteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;

            default:
                updated = 0;
                break;
        }
        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updated;
    }
}
