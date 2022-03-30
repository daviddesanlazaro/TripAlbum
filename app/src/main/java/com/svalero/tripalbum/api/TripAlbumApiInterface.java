package com.svalero.tripalbum.api;

import com.svalero.tripalbum.domain.Country;
import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.Visit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TripAlbumApiInterface {
    @GET("countries")
    Call<List<Country>> getCountries();

    @GET("country/{countryId}/provinces")
    Call<List<Province>> getProvinces(@Path("countryId") long countryId);

    @GET("province/{provinceId}/places")
    Call<List<Place>> getPlaces(@Path("provinceId") long provinceId);

    @GET("user/{userId}/place/{placeId}/visits")
    Call<List<Visit>> getVisits(@Path("userId") long userId, @Path("placeId") long placeId);

    @POST("visits")
    Call<Visit> addVisit(@Body Visit visit);
}
