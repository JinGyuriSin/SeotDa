package com.appknot.seotda.ui.user

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.appknot.seotda.R
import com.appknot.seotda.extensions.plusAssign
import com.appknot.seotda.rx.AutoClearedDisposable
import com.appknot.seotda.ui.BaseActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: UserViewModelFactory

    lateinit var viewModel: UserViewModel

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

        viewDisposables += viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLoading ->
                if (isLoading) showLoadingDialog()
                else hideLoadingDialog()
            }

        viewDisposables += viewModel.message
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message -> showSnackbar(message) }


        viewDisposables += btn_enter.clicks().subscribe {
            val id = tiet_id.text.toString()

            disposables += viewModel.requestRegisterToken(id, fbToken)
        }

        viewDisposables += viewModel.data
            .filter { !it.isEmpty }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { startActivity(intent) }
    }
}
