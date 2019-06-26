package com.appknot.seotda.ui.main

import com.appknot.seotda.api.UserApi
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
class MainViewModel(val api: UserApi, val userProvider: UserProvider) : BaseViewModel() {

    fun loadUserIdx(): Disposable =
        Single.fromCallable { optionalOf(userProvider.userIdx) }
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer<SupportOptional<String>>   {
                requestExitRoom(it.value)
            })

    fun requestExitRoom(userIdx: String): Disposable =
        api.leaveFromRoom(userIdx).api()
            .map { optionalOf(it.data) }
            .doOnSubscribe { isLoading.onNext(true) }
            .doFinally { isLoading.onNext(false) }
            .subscribe({
                data.onNext(it)
            }) {
                message.onNext(it.message.toString())
            }
}