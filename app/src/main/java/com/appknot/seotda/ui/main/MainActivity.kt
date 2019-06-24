package com.appknot.seotda.ui.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.appknot.seotda.R
import com.appknot.seotda.extensions.plusAssign
import com.appknot.seotda.model.IDProvider
import com.appknot.seotda.ui.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    lateinit var viewModel: MainViewModel

    @Inject lateinit var idProvider: IDProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        )[MainViewModel::class.java]

        lifecycle += disposables
        lifecycle += viewDisposables

        viewDisposables += viewModel.message
            .subscribe { message -> showSnackbar(message) }
        
        viewDisposables += btn_run.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val clCenterRect = locateView(cl_center)

                iv_me_first_card.animPay(
                    clCenterRect.left.toFloat(),
                    clCenterRect.right.toFloat(),
                    clCenterRect.bottom.toFloat(),
                    clCenterRect.top.toFloat()
                )
            }

        viewDisposables += btn_exit.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                disposables += viewModel.requestExitRoom(idProvider.id.toString())
            }
    }

    fun ImageView.animPay(xFrom: Float, xTo: Float, yFrom: Float, yTo: Float) {

        val animation = AnimationSet(false)
        animation.fillAfter = true

//        val scale = ScaleAnimation(1.0f, xScale, 1.0f, yScale)
//        scale.duration = 1000

        val trans = TranslateAnimation(xFrom, xTo, yFrom, yTo)
        trans.duration = 1000

//        animation.addAnimation(scale)
        animation.addAnimation(trans)

        this.startAnimation(animation)
    }

    fun locateView(view: View): Rect {
        val loc = Rect()
        val location = intArrayOf(0, 0)
        if (view == null) return loc

        view.getLocationOnScreen(location)

        loc.left = location[0]
        loc.top = location[1]
        loc.right = loc.left + view.width
        loc.bottom = loc.top + view.height

        return loc
    }
}
