package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.graphics.Typeface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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


    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/MontserratAlternates-Bold.otf");
        String judul = getIntent().getStringExtra(EXTRA_JUDUL);
        tvJudul.setText(judul);
        tvJudul.setTypeface(typeface);
        Typeface typefacee = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-R.ttf");
        String tanggalrilis = getIntent().getStringExtra(EXTRA_TANGGAL);
        tvDate.setText(tanggalrilis);
        tvDate.setTypeface(typefacee);
        String deskripsi = getIntent().getStringExtra(EXTRA_DETAIL);
        tvDescription.setText(deskripsi);
        tvDescription.setTypeface(typefacee);

        String subtitle = getIntent().getStringExtra(EXTRA_BAHASA);
        tvSubtitle.setText(subtitle);
        tvSubtitle.setTypeface(typefacee);
        String popular = getIntent().getStringExtra(EXTRA_POPULAR);
        tvPopularity.setText(popular);
        tvPopularity.setTypeface(typefacee);
        String voting = getIntent().getStringExtra(EXTRA_VOTEAVG);
        tvVoting.setText(voting);
        tvVoting.setTypeface(typefacee);
        toggleButton.setChecked(false);
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_red));
                else
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white));
            }
        });


        String poster = getIntent().getStringExtra(EXTRA_POSTER);
        Glide
                .with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" + poster)
                .into(imgPoster);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(tanggalrilis);

            SimpleDateFormat new_date = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String date_rilis = new_date.format(date);
            tvDate.setText(date_rilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
}
