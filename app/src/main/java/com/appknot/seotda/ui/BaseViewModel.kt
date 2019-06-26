package com.appknot.seotda.ui

import androidx.lifecycle.ViewModel
import com.appknot.seotda.util.SupportOptional
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author Jin on 2019-06-17
 */
open class BaseViewModel : ViewModel(){

    val data: BehaviorSubject<SupportOptional<Any>> = BehaviorSubject.create()

    val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    val message: PublishSubject<String> = PublishSubject.create()
}