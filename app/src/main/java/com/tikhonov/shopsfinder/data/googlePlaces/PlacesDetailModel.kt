package com.tikhonov.shopsfinder.data.googlePlaces

import com.tikhonov.shopsfinder.data.model.PoiDetails

class PlaceDetailsResults(
    private val result: PlaceDetailsResult
) {
    fun convertToPoiDetails(): PoiDetails =
            PoiDetails(
                placeId = result.place_id,
                address = result.formatted_address,
                phoneNumber = result.formatted_phone_number,
                website = result.website ,
                rating = result.rating,
                name = result.name,
                url = result.url
            )
        }

class PlaceDetailsResult(
    val place_id: String,
    val formatted_address: String?,
    val formatted_phone_number: String?,
    val website: String?,
    val rating: Double?,
    val name: String?,
    val url: String?
)
