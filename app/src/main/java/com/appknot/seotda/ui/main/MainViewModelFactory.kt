package com.appknot.seotda.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.model.UserProvider

/**
 *
 * @author Jin on 2019-06-21
 */
class MainViewModelFactory(val api: SeotDaApi, val userProvider: UserProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(api, userProvider) as T
    }
}