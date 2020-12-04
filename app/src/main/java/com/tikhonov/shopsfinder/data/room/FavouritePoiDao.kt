package com.tikhonov.shopsfinder.data.room

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface FavouritePoiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(poi: FavoritePoi)

    @Delete
    suspend fun delete(poi: FavoritePoi)

    @Query("select * from favourite_poi")
    fun getPoiAsLiveData(): LiveData<List<FavoritePoi>>

    @Query("select * from favourite_poi where placeId = :placeId")
    suspend fun getPoi(placeId: String): FavoritePoi?
}