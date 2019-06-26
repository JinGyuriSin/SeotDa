package com.appknot.seotda.model

import android.content.Context
import android.preference.PreferenceManager

/**
 *
 * @author Jin on 2019-06-21
 */
class UserProvider(private val context: Context) {

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_USER_IDX = "user_idx"
    }

    fun updateID(id: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(KEY_ID, id)
            .apply()
    }

    val id: String?
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_ID, null)

    fun updateUserIdx(idx: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(KEY_USER_IDX, idx)
            .apply()
    }

    val userIdx: String
        get() = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_USER_IDX, null)
}