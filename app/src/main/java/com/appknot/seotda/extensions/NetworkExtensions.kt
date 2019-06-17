package com.appknot.seotda.extensions

import android.content.Context
import com.appknot.seotda.api.ApiResponse
import com.appknot.seotda.api.ApiResponseException
import com.appknot.seotda.ui.BaseActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author Jin on 2019-06-14
 */

val CODE_SUCCESS = "0"

fun <T : ApiResponse> Single<T>.api(context: Context): Single<T> =
    withProgress(context)
        .flatMap { response ->
            when (response.code) {
                CODE_SUCCESS -> Single.just(response)
                else -> Single.error(ApiResponseException(response))
            }
        }

fun <T : ApiResponse> Single<T>.apiWithoutProgress(): Single<T> =
    networkThread()
        .flatMap { response ->
            when (response.code) {
                CODE_SUCCESS -> Single.just(response)
                else -> Single.error(ApiResponseException(response))
            }
        }

fun <T> Single<T>.networkThread(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.withProgress(context: Context?): Single<T> =
    networkThread()
        .doOnSubscribe { disposable ->
            AndroidSchedulers.mainThread().scheduleDirect {
                (context as BaseActivity).showLoadingDialog()
            }
        }
        .doFinally {
            (context as BaseActivity).hideLoadingDialog()
        }