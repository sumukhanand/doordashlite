package com.sumukha.doordashlite.data.data_sources;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sumukha.doordashlite.data.models.Restaurant;
import com.sumukha.doordashlite.ui.presenters.BasePresenter;
import com.sumukha.doordashlite.util.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RemoteDataSource {

    @dagger.Component
    @Singleton
    public interface Component {
        void inject(BasePresenter presenter);
    }

    private DoorDashAPI api;

    @Inject
    public RemoteDataSource() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit jsonPlaceholderRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = jsonPlaceholderRetrofit.create(DoorDashAPI.class);
    }

    public Observable<List<Restaurant>> fetchRestaurants(String lat, String lng) {
        return api.getRestaurants(lat, lng);
    }
}
