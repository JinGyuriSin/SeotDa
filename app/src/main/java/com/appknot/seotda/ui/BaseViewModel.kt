package com.appknot.seotda.ui

import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 *
 * @author Jin on 2019-06-17
 */
open class BaseViewModel : ViewModel(){

    val message: PublishSubject<String> = PublishSubject.create()

    val isLoading: BehaviorSubject<Boolean>
            = BehaviorSubject.createDefault(false)
}