package com.appknot.seotda.di

import com.appknot.seotda.App
import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.main.MainViewModel
import com.appknot.seotda.ui.user.UserViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author Jin on 2019-06-07
 */


val appContext = module {
    single(named("appContext")) { androidContext() }
}

fun getUrl(): String = if (App.isTestServer) App.API_HOST_TEST else App.API_HOST

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(getUrl())
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build()
            )
            .build()
            .create(SeotDaApi::class.java)
    }
}

val userProviderModule = module {
    single {
        UserProvider(get())
    }
}

val mainModule = module {
    viewModel { MainViewModel(get(), get()) }
}

val userModule = module {
    viewModel { UserViewModel(get(), get()) }
}