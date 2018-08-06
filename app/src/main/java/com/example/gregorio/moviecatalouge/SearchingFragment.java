package com.example.gregorio.moviecatalouge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class SearchingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    FilmAdapter adapter;
    Button search;
    ListView listView;
    EditText edtSearch;

    static final String EXTRA_FILM = "EKSTRA FILM";


    public SearchingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        adapter = new FilmAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView = (ListView) view.findViewById(R.id.list_View);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilmItems items = (FilmItems) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_JUDUL, items.getJudulFilm());
                intent.putExtra(DetailActivity.EXTRA_TANGGAL, items.getTanggal());
                intent.putExtra(DetailActivity.EXTRA_DETAIL, items.getDeskripsi());
                intent.putExtra(DetailActivity.EXTRA_BAHASA, items.getSubtitle());
                intent.putExtra(DetailActivity.EXTRA_POPULAR, items.getPopular());
                intent.putExtra(DetailActivity.EXTRA_VOTEAVG, items.getVote());
                intent.putExtra(DetailActivity.EXTRA_POSTER, items.getPoster());
                startActivity(intent);
            }
        });

        search = (Button) view.findViewById(R.id.btn_search);
        search.setOnClickListener(searchListener);
        edtSearch = (EditText)view.findViewById(R.id.edt_cari);
        String cari = edtSearch.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_FILM, cari);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String judulFilm = edtSearch.getText().toString();
            if (TextUtils.isEmpty(judulFilm)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_FILM, judulFilm);
            getLoaderManager().restartLoader(0, bundle, SearchingFragment.this);
        }
    };

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int i, Bundle bundle) {
        String judulFilm = "";
        if (bundle != null) {
            judulFilm = bundle.getString(EXTRA_FILM);
        }
        return new MyAsyncTaskLoad(getActivity(), judulFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }
}
