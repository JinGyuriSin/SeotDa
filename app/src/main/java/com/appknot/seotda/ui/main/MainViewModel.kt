package com.appknot.seotda.ui.main

import com.appknot.seotda.api.UserApi
import com.appknot.seotda.extensions.api
import com.appknot.seotda.ui.BaseViewModel
import com.appknot.seotda.util.optionalOf
import io.reactivex.disposables.Disposable

/**
 *
 * @author Jin on 2019-06-21
 */
class MainViewModel(val api: UserApi) : BaseViewModel() {

    fun requestExitRoom(id: String): Disposable =
        api.leaveFromRoom(id).api()
            .map { optionalOf(it.data) }
            .doOnSubscribe { isLoading.onNext(false) }
            .doFinally { isLoading.onNext(true) }
            .subscribe({
                data.onNext(it)
            }) {

            }
}