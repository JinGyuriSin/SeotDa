package com.appknot.seotda.di

import com.appknot.seotda.App
import com.appknot.seotda.App.Companion.API_HOST
import com.appknot.seotda.App.Companion.API_HOST_TEST
import com.appknot.seotda.api.RetrofitAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *
 * @author Jin on 2019-06-14
 */

@Module
class ApiModule {


    var host = API_HOST

    fun getUrl(): String {

        host = if (App.isTestServer) API_HOST_TEST else API_HOST

        return host
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
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

    @Provides
    @Singleton
    fun provideExample(retrofit: Retrofit) =
        retrofit.create(RetrofitAPI.Example::class.java)
}