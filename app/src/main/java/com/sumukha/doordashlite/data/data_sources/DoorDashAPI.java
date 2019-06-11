package com.sumukha.doordashlite.data.data_sources;

import com.sumukha.doordashlite.data.models.Restaurant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DoorDashAPI {

    String RESTAURANTS_ENDPOINT = "/v2/restaurant/?offset=0&limit=50";

    @GET(RESTAURANTS_ENDPOINT)
    Observable<List<Restaurant>> getRestaurants(@Query("lat") String lat, @Query("lng") String lng);
}
