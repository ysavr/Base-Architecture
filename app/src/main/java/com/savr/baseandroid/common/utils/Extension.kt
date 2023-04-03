package com.savr.baseandroid.common.utils

import android.app.Activity
import android.view.View
import android.widget.Toast

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Activity.toast(message: String, isLong: Boolean = false) {
    if (isLong) Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}