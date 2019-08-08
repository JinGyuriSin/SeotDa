package com.appknot.seotda.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.appknot.seotda.R
import com.appknot.seotda.api.model.Player
import com.appknot.seotda.api.model.User
import com.appknot.seotda.extensions.hideLoadingDialog
import com.appknot.seotda.extensions.plusAssign
import com.appknot.seotda.extensions.showLoadingDialog
import com.appknot.seotda.extensions.showSnackbar
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.BaseActivity
import com.appknot.seotda.ui.GlideApp
import com.appknot.seotda.util.convertCurrency
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.module_profile_user.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    val viewModel: MainViewModel by viewModel(clazz = MainViewModel::class)

    val userProvider: UserProvider by inject()

    lateinit var userList: ArrayList<User>

    val userViewList = ArrayList<View>()
    val sortByMe = ArrayList<User>()

    lateinit var receiver: BroadcastReceiver

    lateinit var player: Player

    enum class Ready(val key: String, val value: Boolean)  {
        NOT_READ("0", false),
        READY("1", true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.getStringExtra(KEY_CODE)) {
                    "1" -> {
                        userList = intent.getSerializableExtra(KEY_USER_LIST) as ArrayList<User>
                        initViews(userList)
                    }
                    "2" -> {
                        val user = intent.getSerializableExtra(KEY_USER) as User
                        userList.remove(user)
                        userList.add(User())
                        initViews(userList)
                    }
                    "3" -> {
                        player = intent.getSerializableExtra(KEY_PLAYER) as Player
                        setReady(player)
                    }
                    "4" -> {

                    }
                }
            }
        }

        IntentFilter().run {
            addAction(ACTION_BROADCAST)
            registerReceiver(receiver, this)
        }

        lifecycle += disposables
        lifecycle += viewDisposables

        viewDisposables += viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLoading ->
                if (isLoading) showLoadingDialog()
                else hideLoadingDialog()
            }

        viewDisposables += viewModel.message
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message -> showSnackbar(message) }

        viewDisposables += viewModel.data
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finish()
            }

        disposables += viewModel.loadUserIdx()

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
                exitRoom()
            }

        viewDisposables += btn_ready.clicks()
            .map { btn_ready.isChecked }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                disposables += when (it) {
                    true -> viewModel.requestReady(Ready.READY.key)
                    false -> viewModel.requestReady(Ready.NOT_READ.key)
                }
            }

        userList = intent.getSerializableExtra(KEY_USER_LIST) as ArrayList<User>

        initViews(userList)
    }

    override fun onStop() {
        exitRoom()
        unregisterReceiver(receiver)
        super.onStop()
    }

    fun exitRoom() {
        disposables += viewModel.requestExitRoom()
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

    fun initViews(userList: ArrayList<User>) {

        sortByMe.clear()

        var me: User? = null

        run loop@{
            userList.forEach { user ->
                if (userProvider.userIdx == user.idx) {
                    me = user
                    return@loop
                }
            }
        }

        me?.let {
            userList.forEach { user ->
                if (user.position.toInt() >= it.position.toInt()) sortByMe.add(user)
            }
            userList.forEach { user ->
                if (user.position.toInt() < it.position.toInt()) sortByMe.add(user)
                else return@let
            }
        }

        me?.let {
            GlideApp.with(this)
                .load(it.profileImgPath)
                .into(iv_me_profile)

            tv_me_money.text = getString(R.string.common_coin, it.coin.toInt().convertCurrency())
        }

        sortByMe.remove(me)

        initUser(sortByMe)

    }

    fun initUser(userList: ArrayList<User>) {

        userViewList.clear()

        userViewList.add(layout_user_first)
        userViewList.add(layout_user_second)
        userViewList.add(layout_user_third)

        userList.forEachIndexed { index, user ->
            GlideApp.with(this)
                .load(user.profileImgPath)
                .into(userViewList[index].iv_profile)

            userViewList[index].tv_id.text = user.id
            userViewList[index].tv_money.text = getString(R.string.common_coin, user.coin.toInt().convertCurrency())
        }
    }

    fun setReady(player: Player) {
        sortByMe.forEachIndexed { index, user ->
            if (user.idx == player.userIdx) {
                userViewList[index].cl_user.isSelected = Ready.values()[player.ready.toInt()].value
                return@forEachIndexed
            }
        }
    }

    companion object {
        const val KEY_USER_LIST = "user_list"
        const val KEY_USER = "user"
        const val KEY_CODE = "code"
        const val ACTION_BROADCAST = "com.appknot.seotda.SEND_BROAD_CAST"
        const val KEY_PLAYER = "player"
        const val KEY_CARD_INFO = "cardInfo"
        const val KEY_SEED_COIN = "seedCoin"
        const val KEY_BOSS = "boss"
    }
}
