package com.sumukha.doordashlite.ui.presenters;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.sumukha.doordashlite.DoorDashLiteApplication;
import com.sumukha.doordashlite.data.data_sources.RemoteDataSource;
import com.sumukha.doordashlite.ui.views.BaseView;
import com.sumukha.doordashlite.ui.views.DiscoverView;

import javax.inject.Inject;

public class BasePresenter implements Presenter {

    BaseView view;

    @Inject
    RemoteDataSource dataSource;

    public BasePresenter(@NonNull BaseView view) {
        this.view = view;
        DoorDashLiteApplication.getComponent().inject(this);
    }

    @VisibleForTesting
    public BasePresenter(DiscoverView view, RemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    @Override
    public void fetchData() {
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}

