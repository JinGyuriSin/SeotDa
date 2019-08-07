package com.appknot.seotda.di.ui

import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.main.MainViewModelFactory
import dagger.Module
import dagger.Provides

/**
 *
 * @author Jin on 2019-06-07
 */

@Module
class MainModule {

    @Provides
    fun provideViewModelFactory(seotDaApi: SeotDaApi, userProvider: UserProvider): MainViewModelFactory =
            MainViewModelFactory(seotDaApi, userProvider)
}