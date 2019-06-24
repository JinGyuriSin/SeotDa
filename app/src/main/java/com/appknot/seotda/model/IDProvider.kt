package com.appknot.seotda.model

import android.content.Context
import android.preference.PreferenceManager

/**
 *
 * @author Jin on 2019-06-21
 */
class IDProvider(private val context: Context) {

    companion object {
        private const val KEY_ID = "id"
    }

    fun updateID(id: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(KEY_ID, id)
            .apply()
    }

    val id: String?
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_ID, null)
}