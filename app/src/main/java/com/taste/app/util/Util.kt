package com.taste.app.util

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun showSnackBar(
    view: View,
    @StringRes text: Int,
    @ColorRes textColor: Int,
    @ColorRes backgroundColor: Int
) {
    val successSnackBar =
        Snackbar.make(view, view.context.getString(text), Snackbar.LENGTH_LONG)
    successSnackBar.setTextColor(view.context.resources.getColor(textColor))
    successSnackBar.view.setBackgroundColor(view.context.resources.getColor(backgroundColor))
    successSnackBar.show()
}