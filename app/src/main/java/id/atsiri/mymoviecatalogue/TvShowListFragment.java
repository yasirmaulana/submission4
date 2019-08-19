package id.atsiri.mymoviecatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import id.atsiri.mymoviecatalogue.adapter.TvShowsAdapter;

public class TvShowListFragment extends Fragment implements View.OnClickListener {
    private TvShowsAdapter adapter;
    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;

    public TvShowListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(this, getTvShow);

        adapter = new TvShowsAdapter();
        adapter.notifyDataSetChanged();

        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        tvShowViewModel.setTvShow();
        showLoading(true);

        adapter.setOnItemClickCallback(new TvShowsAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShowItems data) {
                showSelectedTvShow(data);
            }
        });
    }

    private Observer<ArrayList<TvShowItems>> getTvShow = new Observer<ArrayList<TvShowItems>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShowItems> tvShowItems) {
            if (tvShowItems != null) {
                adapter.setData(tvShowItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void showSelectedTvShow(TvShowItems tvShow) {
        Intent tvShowDetail = new Intent(getActivity(), TvShowDetailActivity.class);
        tvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
        startActivity(tvShowDetail);
    }
}
