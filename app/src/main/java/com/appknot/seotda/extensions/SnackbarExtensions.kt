package com.appknot.seotda.extensions

import android.app.Activity
import androidx.annotation.StringRes
import com.appknot.seotda.R
import com.google.android.material.snackbar.Snackbar

/**
 *
 * @author Jin on 2019-06-25
 */



fun Activity.showSnackbar(msg: String) {
    Snackbar.make(this.window.decorView.findViewById(android.R.id.content),
        msg,
        Snackbar.LENGTH_LONG)
        .setActionTextColor(getColor(R.color.colorSecondary))
        .show()
}


fun Activity.showSnackbar(@StringRes StringResId: Int) {
    showSnackbar(getString(StringResId))
}
