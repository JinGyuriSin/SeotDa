package com.appknot.seotda.ui.user

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
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    lateinit var viewModel: UserViewModel

    @Inject
    lateinit var userProvider: UserProvider

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

        viewDisposables += viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLoading ->
                if (isLoading) showLoadingDialog()
                else hideLoadingDialog()
            }

        viewDisposables += viewModel.message
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message -> showSnackbar(message) }


        viewDisposables += btn_enter.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val id = tiet_id.text.toString()

                if ("" == id) Single.error<String>(Throwable("아이디를 입력해주세요"))

                userProvider.updateID(id)

                disposables += viewModel.requestRegisterToken(id, fbToken)
            }) {
                showSnackbar(it.message.toString())
            }

        viewDisposables += viewModel.data
            .filter { !it.isEmpty }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(
                    intentFor<MainActivity>()
                        .clearTask()
                        .newTask()
                )
            }
    }
}
