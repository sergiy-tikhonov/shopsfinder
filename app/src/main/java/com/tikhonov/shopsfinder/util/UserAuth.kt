package com.tikhonov.shopsfinder.util

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserAuth @Inject constructor() {

    fun userLoggedIn() = FirebaseAuth.getInstance().currentUser != null
    val currentUser = FirebaseAuth.getInstance().currentUser
    fun logOut(context: Context, block: ()-> Unit) {
        AuthUI.getInstance().signOut(context).addOnCompleteListener {
            block()
        }
    }
}