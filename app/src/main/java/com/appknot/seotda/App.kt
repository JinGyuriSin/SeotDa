package com.appknot.seotda

import android.util.Log
import com.appknot.seotda.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 *
 * @author Jin on 2019-06-07
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    fun logMessage(message: String) {
        Log.d("@@ ${getString(R.string.app_name)}", message)
    }

    companion object {

        lateinit var app : App

        var isTestServer = false
        // API Host - 운영 서버
        const val API_HOST = "http://ethan.appknot.com"
        // API Host - 개발 서버
        const val API_HOST_TEST = ""
    }
}