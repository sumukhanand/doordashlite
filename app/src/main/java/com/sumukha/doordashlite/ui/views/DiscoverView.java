package com.sumukha.doordashlite.ui.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumukha.doordashlite.R;
import com.sumukha.doordashlite.data.models.Restaurant;
import com.sumukha.doordashlite.ui.adapters.DiscoverAdapter;
import com.sumukha.doordashlite.ui.presenters.DiscoverPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverView extends BaseView {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.view_recycler)
    RecyclerView recyclerView;

    DiscoverAdapter adapter = new DiscoverAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_discover);
        setTitle(getString(R.string.title_discover));
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);

        presenter = new DiscoverPresenter(this);
        presenter.fetchData();
    }

    public void onFetchDataSuccess(@NonNull List<Restaurant> restaurants) {
        progressBar.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);

        adapter.setRestaurants(restaurants);
    }
}
