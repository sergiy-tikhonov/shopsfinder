package com.tikhonov.shopsfinder.util

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.tikhonov.shopsfinder.ui.SingleActivity

val Fragment.parentActivity: SingleActivity
    get() = requireActivity() as SingleActivity

fun Fragment.setToolbar(toolbar: Toolbar, showMenu: Boolean = false) {
    parentActivity.setSupportActionBar(toolbar)
    if (showMenu) {
        setHasOptionsMenu(true)
    }
}

fun Fragment.setNavigationUp(toolbar: Toolbar) {
    parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    parentActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
    toolbar.setNavigationOnClickListener {
        parentActivity.onBackPressed()
    }
}

fun Fragment.navigateTo(fragment: Fragment, addToBackStack: Boolean) {
    parentActivity.navigateTo(fragment, addToBackStack = addToBackStack)
}

