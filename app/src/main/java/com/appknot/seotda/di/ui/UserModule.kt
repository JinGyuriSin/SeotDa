package com.appknot.seotda.di.ui

import android.content.Context
import com.appknot.seotda.api.UserApi
import com.appknot.seotda.ui.user.UserActivity
import com.appknot.seotda.ui.user.UserViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 *
 * @author Jin on 2019-06-12
 */

@Module
class UserModule {

    @Provides
    fun provideViewModelFactory(activity: UserActivity, userApi: UserApi): UserViewModelFactory =
        UserViewModelFactory(activity, userApi)
}