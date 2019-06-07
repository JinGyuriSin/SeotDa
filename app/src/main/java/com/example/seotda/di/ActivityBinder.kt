package com.example.seotda.di

import com.example.seotda.di.ui.MainModule
import com.example.seotda.ui.main.MainActivity
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