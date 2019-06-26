package com.appknot.seotda.di

import android.content.Context
import com.appknot.seotda.model.UserProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 * @author Jin on 2019-06-07
 */

@Module
class LocalDataModule {

    // SharedPreferences

    @Provides
    @Singleton
    fun provideUserProvider(@Named("appContext") context: Context): UserProvider = UserProvider(context)
    
}