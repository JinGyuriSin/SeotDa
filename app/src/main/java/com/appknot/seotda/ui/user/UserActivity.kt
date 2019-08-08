package com.appknot.seotda.ui.user

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.appknot.seotda.R
import com.appknot.seotda.api.model.User
import com.appknot.seotda.extensions.*
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.BaseActivity
import com.appknot.seotda.ui.main.MainActivity
import com.appknot.seotda.ui.main.MainActivity.Companion.ACTION_BROADCAST
import com.appknot.seotda.ui.main.MainActivity.Companion.KEY_USER_LIST
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class UserActivity : BaseActivity() {

    val viewModel: UserViewModel by viewModel(clazz = UserViewModel::class)

    val userProvider: UserProvider by inject()

    lateinit var userList: ArrayList<User>

    lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        lifecycle += disposables
        lifecycle += viewDisposables

        var fbToken = ""
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            fbToken = it.token
        }

        tiet_id.setText(userProvider.id)

//        viewDisposables += viewModel.isLoading
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe { isLoading ->
//                if (isLoading) showLoadingDialog()
//                else hideLoadingDialog()
//            }

        viewDisposables += viewModel.message
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message -> showSnackbar(message) }


        val timer = Observable.timer(10L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        viewDisposables += btn_enter.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showLoadingDialog()

                val id = tiet_id.text.toString()

                if ("" == id) Single.error<String>(Throwable("아이디를 입력해주세요"))

                userProvider.updateID(id)

                disposables += viewModel.requestRegisterToken(id, fbToken)

                viewDisposables += timer
                    .subscribe({
                        hideLoadingDialog()
                        error(getString(R.string.common_api_request_failed))
                    })  {
                        showSnackbar(R.string.common_api_request_failed)
                    }
            }) {
                showSnackbar(it.message.toString())
            }


        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                hideLoadingDialog()

                userList = intent.getSerializableExtra(KEY_USER_LIST) as ArrayList<User>

                startActivity(
                    intentFor<MainActivity>(
                        KEY_USER_LIST to userList
                    )
                        .clearTask()
                        .newTask()
                )
            }
        }

        IntentFilter().run {
            addAction(ACTION_BROADCAST)
            registerReceiver(receiver, this)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
