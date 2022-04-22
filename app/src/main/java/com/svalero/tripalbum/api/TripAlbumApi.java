package com.svalero.tripalbum.api;

import static com.svalero.tripalbum.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripAlbumApi {

    public static TripAlbumApiInterface buildInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(TripAlbumApiInterface.class);
    }
}
