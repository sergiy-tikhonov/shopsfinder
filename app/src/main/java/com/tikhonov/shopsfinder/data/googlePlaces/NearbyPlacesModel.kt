package com.tikhonov.shopsfinder.data.googlePlaces

import com.tikhonov.shopsfinder.data.model.Poi

class NearByPlacesResults (
    private val results: List<NearByPlacesResult>
) {
    fun convertToPoi(): List<Poi> {
        return results.map { item ->
            Poi(
                lat = item.geometry.location.lat,
                long = item.geometry.location.lng,
                name = item.name,
                rating = item.rating,
                photoUrl = "",
                numberOfRates = item.user_ratings_total,
                placeId = item.place_id
            )
        }
    }
}

class NearByPlacesResult (
    val name: String,
    val place_id: String,
    val rating: Double,
    val user_ratings_total: Int,
    val geometry: GeometryLocation
)

class GeometryLocation (
    val location: Location
)

class Location (
    val lat: Double,
    val lng: Double
)