package com.appknot.seotda.ui.user

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appknot.seotda.api.UserApi

/**
 *
 * @author Jin on 2019-06-11
 */

class UserViewModelFactory(val context: Context, val api: UserApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserViewModel(context, api) as T
    }
}