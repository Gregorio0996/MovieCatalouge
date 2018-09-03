package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilmAdapter extends BaseAdapter {

    private ArrayList<FilmItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String final_overview;

    public FilmAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<FilmItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public FilmItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.film_items, null);
            holder.textViewNamaFilm = (TextView) convertView.findViewById(R.id.textFilm);
            holder.textViewDesc = (TextView) convertView.findViewById(R.id.textDesc);
            holder.textViewTanggal = (TextView) convertView.findViewById(R.id.textTanggal);
            holder.imgPoster = (ImageView) convertView.findViewById(R.id.img_poster);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewNamaFilm.setText(mData.get(position).getJudulFilm());
        holder.textViewDesc.setText(mData.get(position).getDeskripsi());
        holder.textViewTanggal.setText(mData.get(position).getTanggal());
        String overview = mData.get(position).getDeskripsi();
        if (TextUtils.isEmpty(overview)) {
            final_overview = "No data";
        } else {
            final_overview = overview;
        }

        holder.textViewDesc.setText(final_overview);
        String retrievedDate = mData.get(position).getTanggal();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrievedDate);
            SimpleDateFormat new_date = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String date_of_release = new_date.format(date);
            holder.textViewTanggal.setText(date_of_release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context).load("http://image.tmdb.org/t/p/w185/" + mData.get(position).getPoster()).into(holder.imgPoster);

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNamaFilm;
        TextView textViewDesc;
        TextView textViewTanggal;
        ImageView imgPoster;
    }
}
