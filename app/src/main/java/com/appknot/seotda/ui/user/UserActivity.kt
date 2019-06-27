package com.appknot.seotda.ui.user

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.appknot.seotda.R
import com.appknot.seotda.api.model.BaseModel
import com.appknot.seotda.api.model.User
import com.appknot.seotda.extensions.*
import com.appknot.seotda.model.UserProvider
import com.appknot.seotda.ui.BaseActivity
import com.appknot.seotda.ui.main.MainActivity
import com.appknot.seotda.ui.main.MainActivity.Companion.KEY_USER_LIST
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    lateinit var viewModel: UserViewModel

    @Inject
    lateinit var userProvider: UserProvider

    lateinit var userList: ArrayList<User>

    lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        )[UserViewModel::class.java]

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


        viewDisposables += btn_enter.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showLoadingDialog()

                val id = tiet_id.text.toString()

                if ("" == id) Single.error<String>(Throwable("아이디를 입력해주세요"))

                userProvider.updateID(id)

//                disposables += viewModel.requestRegisterToken(id, fbToken)

            }) {
                showSnackbar(it.message.toString())
            }

        viewDisposables += Single.timer(10L, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideLoadingDialog()
            }) {
                showSnackbar("통신에 실패했습니다.")
            }

        receiver = object : BroadcastReceiver()   {
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
            addAction("com.appknot.seotda.SEND_BROAD_CAST")
            registerReceiver(receiver, this)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
