package com.appknot.seotda.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.model.UserProvider

/**
 *
 * @author Jin on 2019-06-11
 */

class UserViewModelFactory(val api: SeotDaApi, val userProvider: UserProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserViewModel(api, userProvider) as T
    }
}