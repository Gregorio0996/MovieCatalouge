package com.example.gregorio.moviecatalouge;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComeFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {


    NowAndUpAdapter adapter;
    Context context;
    RecyclerView mRecyclerView;
    private ArrayList<FilmItems> upComData;

    public UpComeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_come, container, false);
        context = view.getContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_upcome);

        adapter = new NowAndUpAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
        return view;

    }

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {
        return new UpAsyncTaskLoad(getContext(), upComData);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> upComingData) {
        adapter.setData(upComingData);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }


}
