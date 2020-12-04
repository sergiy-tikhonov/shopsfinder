package com.tikhonov.shopsfinder.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePoi::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favouritePoiDAO(): FavouritePoiDao

    // Singleton is implemented with Dagger, so there is no getInstance()-code here
}