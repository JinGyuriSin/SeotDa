package com.appknot.seotda.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @author Jin on 2019-06-11
 */

class UserViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserViewModel() as T
    }
}