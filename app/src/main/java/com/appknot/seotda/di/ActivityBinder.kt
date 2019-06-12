package com.appknot.seotda.di

import com.appknot.seotda.di.ui.MainModule
import com.appknot.seotda.di.ui.UserModule
import com.appknot.seotda.ui.main.MainActivity
import com.appknot.seotda.ui.user.UserListActivity
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

    @ContributesAndroidInjector(modules = [UserModule::class])
    abstract fun bindUserListactivity(): UserListActivity
}