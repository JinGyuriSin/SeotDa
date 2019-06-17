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
import com.appknot.seotda.api.ExampleApi
import com.appknot.seotda.extensions.api
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import java.util.*
import javax.inject.Inject

/**
 *
 * @author Jin on 2019-06-12
 */
open class BaseActivity : AppCompatActivity()    {

    protected lateinit var context: Context
    private var loadingDialogList: ArrayList<AlertDialog?> = ArrayList()

    @Inject lateinit var exampleApi: ExampleApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this


        //example
        exampleApi.login("MAGIC_TOKEN").api(
            context
        )
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