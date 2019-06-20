package com.appknot.seotda.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.appknot.seotda.R
import com.appknot.seotda.api.ApiResponse
import com.appknot.seotda.rx.AutoClearedDisposable
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Response
import java.util.*

/**
 *
 * @author Jin on 2019-06-12
 */
open class BaseActivity : DaggerAppCompatActivity()    {
    
    internal val disposables = AutoClearedDisposable(this)

    internal val viewDisposables = AutoClearedDisposable(lifecycleOwner = this, alwaysClearOnStop = false)

    protected lateinit var context: Context
    private var loadingDialogList: ArrayList<AlertDialog?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    fun showLoadingDialog() {
        loadingDialogList.add(
            AlertDialog.Builder(context)
                .setView(View.inflate(context, R.layout.dialog_progress, null))
                .setCancelable(false)
                .create().apply {
                    window?.setBackgroundDrawableResource(android.R.color.transparent)
                    window?.setDimAmount(0F)
                    show()
                }
        )
    }

    fun hideLoadingDialog() {
        loadingDialogList.forEach { it?.dismiss() }
        loadingDialogList.clear()
    }

    fun showSnackbar(msg: String) {
        Snackbar.make(window.decorView.findViewById(android.R.id.content),
            msg,
            Snackbar.LENGTH_LONG)
            .setActionTextColor(getColor(R.color.colorSecondary))
            .show()
    }

    fun showSnackbar(@StringRes StringResId: Int) {
        showSnackbar(getString(StringResId))
    }

    fun showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, msg, length).show()
    }

    fun showToast(@StringRes StringResId: Int, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, getString(StringResId), length).show()
    }

    fun checkStatusCode(response: Response<ApiResponse>) {
        if (response.code() != 200) {

        }
    }
}