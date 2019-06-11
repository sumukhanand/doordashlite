package com.sumukha.doordashlite.ui.presenters;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.sumukha.doordashlite.data.data_sources.RemoteDataSource;
import com.sumukha.doordashlite.ui.views.DiscoverView;
import com.sumukha.doordashlite.util.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiscoverPresenter extends BasePresenter {
    public DiscoverPresenter(@NonNull DiscoverView view) {
        super(view);
    }

    @VisibleForTesting
    public DiscoverPresenter(@NonNull DiscoverView view, @NonNull RemoteDataSource dataSource) {
        super(view, dataSource);
    }

    @Override
    public void fetchData() {
        super.fetchData();

        dataSource.fetchRestaurants(Constants.HQ_LAT, Constants.HQ_LNG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurants -> {
                    if (view != null && view instanceof DiscoverView) {
                        ((DiscoverView) view).onFetchDataSuccess(restaurants);
                    }
                }, error -> {
                    if (view != null) {
                        view.onFetchDataError(error);
                    }
                });
    }
}
