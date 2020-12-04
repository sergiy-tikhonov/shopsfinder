package com.tikhonov.shopsfinder.ui.maps

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.shopsfinder.data.repository.PlacesRepository
import com.tikhonov.shopsfinder.data.model.Poi
import com.tikhonov.shopsfinder.util.UserAuth
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val placesRepository: PlacesRepository,
    private val userAuth: UserAuth
) : ViewModel(){

    val nearbyPlaces = MutableLiveData<List<Poi>>()

    fun getNearByPlaces(latitude: Double, longitude: Double, radius: Double, type: String) {
        viewModelScope.launch {
            runCatching {
                nearbyPlaces.value = placesRepository.getNearByPlaces(
                    latitude = latitude,
                    longitude = longitude,
                    radius = radius,
                    type = type
                )
            }

        }
    }

    fun logOut(context: Context, block: ()-> Unit) = userAuth.logOut(context, block)
}