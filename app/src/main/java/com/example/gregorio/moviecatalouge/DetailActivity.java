package com.example.gregorio.moviecatalouge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.gregorio.moviecatalouge.DatabaseContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {
    public static String MOVIE_ITEM = "movie_item";
    public static String EXTRA_JUDUL = "extra_judul";
    public static String EXTRA_DETAIL = "extra_deskripsi";
    public static String EXTRA_POSTER = "extra_gambar";
    public static String EXTRA_TANGGAL = "extra_rilis";
    public static String EXTRA_BAHASA = "extra_sub";
    public static String EXTRA_POPULAR = "extra_popularity";
    public static String EXTRA_VOTEAVG = "extra_vote_average";
    @BindView(R.id.img_poster)
    ImageView imgPoster;
    @BindView(R.id.tv_judul)
    TextView tvJudul;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    @BindView(R.id.tv_popularity)
    TextView tvPopularity;
    @BindView(R.id.tv_voting)
    TextView tvVoting;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;

    private FavouriteHelper favouriteHelper;
    private Context context;
    private FilmItems filmItems;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        favouriteHelper = new FavouriteHelper(this);
        favouriteHelper.open();


        final Uri uri = getIntent().getData();

        /*final FilmModel film = getIntent().getParcelableExtra(MOVIE_ITEM);
        tvJudul.setText(film.getJudulFilm());
        tvDate.setText(film.getTanggal());
        tvDescription.setText(film.getDeskripsi());
        tvVoting.setText(film.getVote());
        tvSubtitle.setText(film.getSubtitle());
        tvPopularity.setText(film.getPopular());
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + film.getPoster()).into(imgPoster);*/

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MontserratAlternates-Bold.otf");
        final String judul = getIntent().getStringExtra(EXTRA_JUDUL);

        tvJudul.setText(judul);
        tvJudul.setTypeface(typeface);
        Typeface typefacee = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-R.ttf");
        final String tanggalrilis = getIntent().getStringExtra(EXTRA_TANGGAL);

        tvDate.setText(tanggalrilis);
        tvDate.setTypeface(typefacee);
        final String deskripsi = getIntent().getStringExtra(EXTRA_DETAIL);

        tvDescription.setText(deskripsi);
        tvDescription.setTypeface(typefacee);
        final String subtitle = getIntent().getStringExtra(EXTRA_BAHASA);

        tvSubtitle.setText(subtitle);
        tvSubtitle.setTypeface(typefacee);
        final String popular = getIntent().getStringExtra(EXTRA_POPULAR);

        tvPopularity.setText(popular);
        tvPopularity.setTypeface(typefacee);
        final String voting = getIntent().getStringExtra(EXTRA_VOTEAVG);
        final String poster = getIntent().getStringExtra(EXTRA_POSTER);
        tvVoting.setText(voting);
        tvVoting.setTypeface(typefacee);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_red));
                    /*String title = filmItems.getJudulFilm();
                    String date = filmItems.getTanggal();
                    String desc = filmItems.getDeskripsi();
                    String sub = filmItems.getSubtitle();
                    String popular = filmItems.getPopular();
                    String vote = filmItems.getVote();
                    String poster = filmItems.getPoster();*/
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseContract.FavouriteColumns.TITLE, judul);
                    contentValues.put(DatabaseContract.FavouriteColumns.DATE, tanggalrilis);
                    contentValues.put(DatabaseContract.FavouriteColumns.DESCRIPTION, deskripsi);
                    contentValues.put(DatabaseContract.FavouriteColumns.SUBTITLE, subtitle);
                    contentValues.put(DatabaseContract.FavouriteColumns.POPULARITY, popular);
                    contentValues.put(DatabaseContract.FavouriteColumns.VOTING, voting);
                    contentValues.put(DatabaseContract.FavouriteColumns.POSTER, poster);
                    getContentResolver().insert(CONTENT_URI, contentValues);
                    toggleButton.setChecked(true);

                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
                    getContentResolver().delete(uri, judul, null);
                    toggleButton.setChecked(false);
                }
            }
        });

        if (uri != null){
            Cursor cursor = getContentResolver().query(uri, null, judul, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst())
                    filmItems = new FilmItems(cursor);
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_red));
                cursor.close();

            }
        }


        Glide
                .with(DetailActivity.this)
                .

                        load("http://image.tmdb.org/t/p/w185/" + poster)
                .

                        into(imgPoster);

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }


}
