package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NowAndUpAdapter extends RecyclerView.Adapter<NowAndUpAdapter.ViewHolder> {
    private ArrayList<FilmItems> mfilmItem = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private String final_overview;

    public NowAndUpAdapter(final Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(ArrayList<FilmItems> items) {
        mfilmItem = items;
        notifyDataSetChanged();
    }

    public void addItem(final FilmItems item) {
        mfilmItem.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public FilmItems getItem(int position) {
        return mfilmItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        if (mfilmItem == null) return 0;
        return mfilmItem.size();
    }

    public void clearData() {
        mfilmItem.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textViewNamaFilm.setText(mfilmItem.get(position).getJudulFilm());
        holder.textViewDesc.setText(mfilmItem.get(position).getDeskripsi());
        holder.textViewTanggal.setText(mfilmItem.get(position).getTanggal());
        String overview = mfilmItem.get(position).getDeskripsi();
        if (TextUtils.isEmpty(overview)) {
            final_overview = "No data";
        } else {
            final_overview = overview;
        }

        holder.textViewDesc.setText(final_overview);
        String retrievedDate = mfilmItem.get(position).getTanggal();
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = format_date.parse(retrievedDate);
            SimpleDateFormat ndate = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String release_date = ndate.format(date);
            holder.textViewTanggal.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context).load("http://image.tmdb.org/t/p/w154/" + mfilmItem.get(position).getPoster()).into(holder.imgPoster);

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (context.getString(R.string.bagikan)) + " " + mfilmItem
                        .get(position)
                        .getJudulFilm(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_JUDUL, mfilmItem.get(position).getJudulFilm());
                intent.putExtra(DetailActivity.EXTRA_TANGGAL, mfilmItem.get(position).getTanggal());
                intent.putExtra(DetailActivity.EXTRA_DETAIL, mfilmItem.get(position).getDeskripsi());
                intent.putExtra(DetailActivity.EXTRA_BAHASA, mfilmItem.get(position).getSubtitle());
                intent.putExtra(DetailActivity.EXTRA_POPULAR, mfilmItem.get(position).getPopular());
                intent.putExtra(DetailActivity.EXTRA_VOTEAVG, mfilmItem.get(position).getVote());
                intent.putExtra(DetailActivity.EXTRA_POSTER, mfilmItem.get(position).getPoster());
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_JUDUL, mfilmItem.get(position).getJudulFilm());
                intent.putExtra(DetailActivity.EXTRA_TANGGAL, mfilmItem.get(position).getTanggal());
                intent.putExtra(DetailActivity.EXTRA_DETAIL, mfilmItem.get(position).getDeskripsi());
                intent.putExtra(DetailActivity.EXTRA_BAHASA, mfilmItem.get(position).getSubtitle());
                intent.putExtra(DetailActivity.EXTRA_POPULAR, mfilmItem.get(position).getPopular());
                intent.putExtra(DetailActivity.EXTRA_VOTEAVG, mfilmItem.get(position).getVote());
                intent.putExtra(DetailActivity.EXTRA_POSTER, mfilmItem.get(position).getPoster());
                context.startActivity(intent);
            }
        });


    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView textViewNamaFilm;
        TextView textViewDesc;
        TextView textViewTanggal;
        Button btn_detail;
        Button btn_share;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            btn_detail = (Button)itemView.findViewById(R.id.btn_set_favorite);
            btn_share = (Button)itemView.findViewById(R.id.btn_set_share);
            textViewDesc = (TextView)itemView.findViewById(R.id.textDesc);
            textViewNamaFilm =(TextView)itemView.findViewById(R.id.textFilm);
            textViewTanggal = (TextView)itemView.findViewById(R.id.textTanggal);
            imgPoster = (ImageView)itemView.findViewById(R.id.img_poster);
        }
    }
}
