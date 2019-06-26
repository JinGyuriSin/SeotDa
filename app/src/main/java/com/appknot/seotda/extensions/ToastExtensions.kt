package com.appknot.seotda.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 *
 * @author Jin on 2019-06-25
 */


fun Context.shortToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context.shortToast(@StringRes StringResId: Int) =
    Toast.makeText(this, getString(StringResId), Toast.LENGTH_SHORT).show()

fun Context.longToast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun Context.longToast(@StringRes StringResId: Int) =
    Toast.makeText(this, getString(StringResId), Toast.LENGTH_LONG).show()
