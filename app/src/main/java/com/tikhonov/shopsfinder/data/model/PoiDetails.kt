package com.tikhonov.shopsfinder.data.model

data class PoiDetails (
    val placeId: String,
    val address: String?,
    val phoneNumber: String?,
    val website: String?,
    val url: String?,
    val rating: Double?,
    val name: String?
)

