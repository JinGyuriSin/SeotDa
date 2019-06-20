package com.appknot.seotda.ui.user

import com.appknot.seotda.api.UserApi
import com.appknot.seotda.extensions.api
import com.appknot.seotda.ui.BaseViewModel
import com.appknot.seotda.util.optionalOf
import io.reactivex.disposables.Disposable

/**
 *
 * @author Jin on 2019-06-11
 */
class UserViewModel(val api: UserApi) : BaseViewModel() {

    fun requestRegisterToken(id: String, fbToken: String): Disposable =
            api.registerToken(id, fbToken).api()
                .doOnSubscribe { isLoading.onNext(true) }
                .doFinally { isLoading.onNext(false) }
                .subscribe({
                    requestEnterRoom(id)
                })  {
                    message.onNext(it.message.toString())
                }

    fun requestEnterRoom(id: String): Disposable =
            api.enterToRoom(id).api()
                .map { optionalOf(it.data) }
                .doOnSubscribe { isLoading.onNext(true) }
                .doFinally { isLoading.onNext(false) }
                .subscribe({
                    data.onNext(it)
                })  {
                    message.onNext(it.message.toString())
                }
}