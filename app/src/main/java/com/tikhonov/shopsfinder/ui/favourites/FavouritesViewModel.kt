package com.tikhonov.shopsfinder.ui.favourites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.shopsfinder.data.room.FavouritePoiDao
import com.tikhonov.shopsfinder.data.room.FavoritePoi
import kotlinx.coroutines.launch

class FavouritesViewModel @ViewModelInject constructor(
    private val favouritePoiDao: FavouritePoiDao
) : ViewModel(){

    val favouriteList = favouritePoiDao.getPoiAsLiveData()

    fun removeFavourite(favouritePoi: FavoritePoi) {
        viewModelScope.launch {
            favouritePoiDao.delete(favouritePoi)
        }
    }
}