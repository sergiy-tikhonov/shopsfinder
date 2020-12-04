package com.tikhonov.shopsfinder.data.googlePlaces

import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {

    @GET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Double,
        @Query("type") type: String,
        @Query("key") key: String
    ): NearByPlacesResults

    @GET("https://maps.googleapis.com/maps/api/place/details/json?")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = "rating,website,formatted_phone_number,formatted_address,place_id,name,url",
        @Query("key") key: String
    ): PlaceDetailsResults
}




