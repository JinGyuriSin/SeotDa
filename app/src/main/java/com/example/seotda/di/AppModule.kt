package com.example.seotda.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 * @author Jin on 2019-06-07
 */

@Module
class AppModule {

    @Provides
    @Named("appContext")
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}