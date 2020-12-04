package com.tikhonov.shopsfinder.data.model

import java.io.Serializable

data class Poi (
    val lat: Double,
    val long: Double,
    val name: String,
    val photoUrl: String,
    val rating: Double,
    val numberOfRates: Int,
    val placeId: String
): Serializable
