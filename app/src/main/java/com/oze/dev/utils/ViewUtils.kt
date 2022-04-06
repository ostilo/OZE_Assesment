package com.oze.dev.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import org.apache.commons.lang3.StringUtils


fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.showSnackBar(message: String, action: String = StringUtils.EMPTY, actionListener: () -> Unit = {}): Snackbar {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    if (action.isNotBlank()) {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.setAction(action) {
            actionListener()
            snackbar.dismiss()
        }
    }
    snackbar.show()
    return snackbar
}
