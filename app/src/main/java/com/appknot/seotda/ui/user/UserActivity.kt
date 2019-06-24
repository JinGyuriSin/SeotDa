package com.appknot.seotda.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.appknot.seotda.R
import com.appknot.seotda.extensions.plusAssign
import com.appknot.seotda.model.IDProvider
import com.appknot.seotda.rx.AutoClearedDisposable
import com.appknot.seotda.ui.BaseActivity
import com.appknot.seotda.ui.main.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    lateinit var viewModel: UserViewModel

    @Inject lateinit var idProvider: IDProvider

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

        tiet_id.setText(idProvider.id)

        viewDisposables += viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isLoading ->
                if (isLoading) showLoadingDialog()
                else hideLoadingDialog()
            }

        viewDisposables += viewModel.message
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message -> showSnackbar(message) }


        viewDisposables += btn_enter.clicks().subscribe({
            val id = tiet_id.text.toString()

            if ("" == id)  Single.error<String>(Throwable("아이디를 입력해주세요"))

            idProvider.updateID(id)

            disposables += viewModel.requestRegisterToken(id, fbToken)
        } ) {
            showSnackbar(it.message.toString())
        }

        viewDisposables += viewModel.data
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(
                    Intent(this, MainActivity::class.java)
//                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
    }
}
