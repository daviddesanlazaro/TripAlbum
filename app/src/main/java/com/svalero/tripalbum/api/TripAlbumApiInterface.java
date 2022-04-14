package com.svalero.tripalbum.api;

import com.svalero.tripalbum.domain.Place;
import com.svalero.tripalbum.domain.Province;
import com.svalero.tripalbum.domain.User;
import com.svalero.tripalbum.domain.Visit;
import com.svalero.tripalbum.domain.VisitDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TripAlbumApiInterface {

    // Provincias
    @GET("provinces")
    Call<List<Province>> getProvinces();

    // Lugares
    @GET("places")
    Call<List<Place>> getAllPlaces(@Query("name") String name);

    @GET("place/{placeId}")
    Call<Place> getPlace(@Path("placeId") String placeId);

    @GET("province/{provinceId}/places")
    Call<List<Place>> getPlaces(@Path("provinceId") String provinceId, @Query("name") String name);

    @GET("user/{userId}/visits")
    Call<List<Visit>> getVisited(@Path("userId") String userId);

    // Visitas
    @GET("user/{userId}/place/{placeId}/visits")
    Call<List<Visit>> getVisits(@Path("userId") String userId, @Path("placeId") String placeId);

    @POST("visits")
    Call<Visit> addVisit(@Body VisitDTO visitDto);

    @PUT("visit/{visitId}")
    Call<Visit> modifyVisit(@Path("visitId") String visitId, @Body VisitDTO visitDto);

    @DELETE("visit/{visitId}")
    Call<Void> deleteVisit(@Path("visitId") String visitId);

    // Usuarios
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users")
    Call<List<User>> getByPhone(@Query("phone") String phone);
}
