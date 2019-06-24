package com.appknot.seotda.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appknot.seotda.api.UserApi

/**
 *
 * @author Jin on 2019-06-21
 */
class MainViewModelFactory(val api: UserApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(api) as T
    }
}