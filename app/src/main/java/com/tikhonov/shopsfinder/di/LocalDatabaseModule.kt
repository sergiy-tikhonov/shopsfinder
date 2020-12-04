package com.tikhonov.shopsfinder.di

import android.content.Context
import androidx.room.Room
import com.tikhonov.shopsfinder.R
import com.tikhonov.shopsfinder.data.room.AppDatabase
import com.tikhonov.shopsfinder.data.room.FavouritePoiDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocalDatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.app_name)
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesFavouritePoiDao(appDatabase: AppDatabase): FavouritePoiDao {
        return appDatabase.favouritePoiDAO()
    }
}