package com.tikhonov.shopsfinder.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.shopsfinder.data.model.PoiDetails
import com.tikhonov.shopsfinder.data.repository.PlacesRepository
import com.tikhonov.shopsfinder.data.room.FavouritePoiDao
import com.tikhonov.shopsfinder.data.room.FavoritePoi
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val placesRepository: PlacesRepository,
    private val favouritePoiDao: FavouritePoiDao
) : ViewModel(){

    val placeDetails = MutableLiveData<PoiDetails>()

    fun getPlaceDetails(placeId: String) {
        viewModelScope.launch {
            runCatching {
                placeDetails.value = placesRepository.getPlaceDetails(placeId = placeId)
            }
        }
    }

    fun saveFavouritePoi(favoritePoi: FavoritePoi) {
        viewModelScope.launch {
            runCatching {
                favouritePoiDao.insert(favoritePoi)
            }
        }
    }
}