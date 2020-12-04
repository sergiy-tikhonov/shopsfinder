package com.tikhonov.shopsfinder.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.tikhonov.shopsfinder.R
import com.tikhonov.shopsfinder.ui.maps.MapsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        if (!viewModel.userLoggedIn())
            showAuthUI()
        else
            showMap()
    }

    fun navigateTo(fragment: Fragment, addToBackStack:Boolean = false) {
      val transaction = supportFragmentManager.beginTransaction()
      transaction.replace(R.id.fragmentContainer, fragment)
      if (addToBackStack)
          transaction.addToBackStack(null)
      transaction.commit()
  }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun showAuthUI(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun showMap(){
        navigateTo(fragment = MapsFragment())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = viewModel.currentUser
                Toast.makeText(this, "Hello, ${user?.displayName}!", Toast.LENGTH_SHORT).show()
                showMap()
            } else {
                showAuthUI()
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }

}