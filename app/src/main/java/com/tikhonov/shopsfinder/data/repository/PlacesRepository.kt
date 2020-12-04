package com.tikhonov.shopsfinder.data.repository

import android.content.Context
import com.tikhonov.shopsfinder.R
import com.tikhonov.shopsfinder.data.googlePlaces.GooglePlacesApi
import com.tikhonov.shopsfinder.data.model.Poi
import com.tikhonov.shopsfinder.data.model.PoiDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PlacesRepository @Inject constructor(private val googlePlacesApi: GooglePlacesApi, @ApplicationContext val appContext: Context) {

    suspend fun getNearByPlaces(latitude: Double, longitude: Double, radius: Double, type: String): List<Poi> {
        return googlePlacesApi.getNearbyPlaces(
            location = "$latitude,$longitude",
            radius = radius,
            type = type,
            key = appContext.resources.getString(R.string.MAPS_API_KEY)
        ).convertToPoi()
    }

    suspend fun getPlaceDetails(placeId: String): PoiDetails {
        return googlePlacesApi.getPlaceDetails(
            placeId = placeId,
            key = appContext.resources.getString(R.string.MAPS_API_KEY)
        ).convertToPoiDetails()
    }
}