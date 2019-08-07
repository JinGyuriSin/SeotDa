package com.appknot.seotda.ui.main

import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.extensions.api
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.BaseViewModel
import com.appknot.seotda.util.SupportOptional
import com.appknot.seotda.util.optionalOf
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author Jin on 2019-06-21
 */
class MainViewModel(val api: SeotDaApi, val userProvider: UserProvider) : BaseViewModel() {

    lateinit var userIdx: String

    fun loadUserIdx(): Disposable =
        Single.fromCallable { optionalOf(userProvider.userIdx) }
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer<SupportOptional<String>>   {
                userIdx = it.value
            })

    fun requestExitRoom(): Disposable =
        api.leaveFromRoom(userIdx).api()
            .map { optionalOf(it.data) }
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .subscribe({
                data.onNext(it)
            }) {
                message.onNext(it.message.toString())
            }

    fun requestReady(status: String): Disposable =
        api.ready(userIdx, status).api()
            .subscribe({

            })  {

            }
}