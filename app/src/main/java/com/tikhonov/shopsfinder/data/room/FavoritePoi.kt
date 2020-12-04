package com.tikhonov.shopsfinder.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_poi")
data class FavoritePoi(
    @PrimaryKey val placeId: String,
    val name: String,
    val address: String,
)