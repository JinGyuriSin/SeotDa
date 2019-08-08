package com.appknot.seotda

import android.app.Application
import android.util.Log
import com.appknot.seotda.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

/**
 *
 * @author Jin on 2019-06-07
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidFileProperties()
            modules(listOf(appContext, apiModule, userProviderModule, mainModule, userModule))
        }

        app = this
    }

    fun logMessage(message: String) {
        Log.d("@@ ${getString(R.string.app_name)}", message)
    }

    companion object {

        lateinit var app : App

        var isTestServer = false
        // API Host - 운영 서버
        const val API_HOST = "http://ethan.appknot.com/SeotDa/g1/api/"
        // API Host - 개발 서버
        const val API_HOST_TEST = ""
    }
}