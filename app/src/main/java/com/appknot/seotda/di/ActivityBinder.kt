package com.appknot.seotda.di

import com.appknot.seotda.di.ui.MainModule
import com.appknot.seotda.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *
 * @author Jin on 2019-06-07
 */

@Module
abstract class ActivityBinder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}