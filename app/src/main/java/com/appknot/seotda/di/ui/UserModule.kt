package com.appknot.seotda.di.ui

import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.user.UserViewModelFactory
import dagger.Module
import dagger.Provides

/**
 *
 * @author Jin on 2019-06-12
 */

@Module
class UserModule {

    @Provides
    fun provideViewModelFactory(seotDaApi: SeotDaApi, userProvider: UserProvider): UserViewModelFactory =
        UserViewModelFactory(seotDaApi, userProvider)
}