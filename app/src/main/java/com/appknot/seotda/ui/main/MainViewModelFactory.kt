package com.appknot.seotda.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appknot.seotda.api.UserApi

/**
 *
 * @author Jin on 2019-06-21
 */
class MainViewModelFactory(val activity: MainActivity, val api: UserApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(activity, api) as T
    }
}