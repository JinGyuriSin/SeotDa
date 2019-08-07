package com.appknot.seotda.ui.user

import com.appknot.seotda.api.SeotDaApi
import com.appknot.seotda.extensions.api
import com.appknot.seotda.extensions.toMap
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.BaseViewModel
import com.appknot.seotda.util.optionalOf
import io.reactivex.disposables.Disposable

/**
 *
 * @author Jin on 2019-06-11
 */
class UserViewModel(val api: SeotDaApi, val userProvider: UserProvider) : BaseViewModel() {

    fun requestRegisterToken(id: String, fbToken: String): Disposable =
            api.registerToken(id, fbToken).api()
                .map { toMap(it.data!!)["user_idx"].toString() }
                .doOnSubscribe { isLoading.onNext(true) }
                .doFinally { isLoading.onNext(false) }
                .subscribe({ userIdx ->
                    userProvider.updateUserIdx(userIdx)
                    requestEnterRoom(userIdx)
                })  {
                    message.onNext(it.message.toString())
                }

    fun requestEnterRoom(userIdx: String): Disposable =
            api.enterToRoom(userIdx).api()
                .map { optionalOf(it.data) }
                .doOnSubscribe { isLoading.onNext(true) }
                .doFinally { isLoading.onNext(false) }
                .subscribe({
                    data.onNext(it)
                })  {
                    message.onNext(it.message.toString())
                }
}