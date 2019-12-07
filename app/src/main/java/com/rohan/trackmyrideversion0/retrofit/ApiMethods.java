package com.rohan.trackmyrideversion0.retrofit;

import com.rohan.trackmyrideversion0.pojos.DistanceMatrixResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiMethods {
    String API_KEY = "AIzaSyDbdBncovqBM56gi5kDWEFHhLSfFB6RZzU";

    @GET("?units=imperial&key=" + API_KEY)
    Call<DistanceMatrixResult> getEarthquakes(@Query("origins") String origins,
                                              @Query("destinations") String destinations);
}
