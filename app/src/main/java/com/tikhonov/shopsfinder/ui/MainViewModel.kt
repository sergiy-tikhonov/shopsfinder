package com.tikhonov.shopsfinder.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.tikhonov.shopsfinder.util.UserAuth

class MainViewModel @ViewModelInject constructor(
    private val userAuth: UserAuth,
    ) : ViewModel(){

    fun userLoggedIn() = userAuth.userLoggedIn()
    val currentUser
        get() = userAuth.currentUser
}