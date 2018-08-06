package com.example.gregorio.moviecatalouge;


import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NowPlayFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {
    NowAndUpAdapter adapter;
    Context context;
    RecyclerView mRecyclerView;
    private ArrayList<FilmItems> nowData;

    public NowPlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_play, container, false);
        context = view.getContext();

        adapter = new NowAndUpAdapter(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_now_play);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        return view;
    }


    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {
        return new NowPAsyncTaskLoad(getContext(), nowData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> nowData) {
        adapter.setData(nowData);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }


}
