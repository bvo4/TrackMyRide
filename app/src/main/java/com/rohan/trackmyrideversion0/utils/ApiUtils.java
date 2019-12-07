package com.rohan.trackmyrideversion0.utils;

import android.location.Location;

import com.google.android.libraries.places.api.model.Place;
import com.rohan.trackmyrideversion0.pojos.DistanceMatrixResult;
import com.rohan.trackmyrideversion0.retrofit.ApiMethods;
import com.rohan.trackmyrideversion0.retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ApiUtils {
    private ApiUtils() {}

    public static void updateTimeForRider(Location driver, Place rider) {
        ApiMethods apiMethods = RetrofitClientInstance.getRetrofitInstance().create(ApiMethods.class);
        String origins = driver.getLatitude() + "," + driver.getLongitude();
        String destinations = driver.getLatitude() + "," + driver.getLongitude();
//        apiMethods.getEarthquakes(origins, destinations).enqueue(new Callback<DistanceMatrixResult>() {
//            @Override
//            public void onResponse(Call<DistanceMatrixResult> call, Response<DistanceMatrixResult> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<DistanceMatrixResult> call, Throwable t) {
//
//            }
//        });
    }
}
