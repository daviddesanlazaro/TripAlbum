package com.svalero.tripalbum.api;

import com.svalero.tripalbum.domain.Favorite;
import com.svalero.tripalbum.domain.FavoriteDTO;
import com.svalero.tripalbum.domain.Friendship;
import com.svalero.tripalbum.domain.FriendshipDTO;
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
    @GET("country/{countryId}/provinces")
    Call<List<Province>> getProvinces(@Path("countryId") long countryId);

    // Lugares
    @GET("places")
    Call<List<Place>> getAllPlaces(@Query("name") String name);

    @GET("province/{provinceId}/places")
    Call<List<Place>> getPlaces(@Path("provinceId") long provinceId, @Query("name") String name);

    @GET("user/{userId}/places")
    Call<List<Place>> getVisited(@Path("userId") long userId);

    @GET("user/{userId}/favoritePlaces")
    Call<List<Place>> getInteresting(@Path("userId") long userId);

    // Visitas
    @GET("user/{userId}/place/{placeId}/visits")
    Call<List<Visit>> getVisits(@Path("userId") long userId, @Path("placeId") long placeId);

    @POST("visits")
    Call<Visit> addVisit(@Body VisitDTO visitDto);

    @PUT("visit/{visitId}")
    Call<Visit> modifyVisit(@Path("visitId") long visitId, @Body VisitDTO visitDto);

    @DELETE("visit/{visitId}")
    Call<Void> deleteVisit(@Path("visitId") long visitId);

    // Usuarios
    @GET("user/{userId}/searchForFriend")
    Call<List<User>> getUsers(@Path("userId") long userId, @Query("phone") String phone);

    // Amigos
    @GET("user/{userId}/friends")
    Call<List<User>> getFriends(@Path("userId") long userId);

    @GET("user/{userId}/user/{friendId}/friendship")
    Call<Friendship> getFriendship(@Path("userId") long userId, @Path("friendId") long friendId);

    @POST("friendships")
    Call<Friendship> addFriend(@Body FriendshipDTO friendshipDTO);

    @DELETE("friendship/{friendshipId}")
    Call<Void> deleteFriendship(@Path("friendshipId") long friendshipId);

    // Favoritos
    @POST("favorites")
    Call<Favorite> addFavorite(@Body FavoriteDTO favoriteDto);
}
