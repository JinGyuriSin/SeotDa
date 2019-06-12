package com.appknot.seotda.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.appknot.seotda.R
import java.util.ArrayList

/**
 *
 * @author Jin on 2019-06-12
 */
open class BaseActivity : AppCompatActivity()    {

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

    fun dismissLoadingDialog() {
        loadingDialogList.forEach { it?.dismiss() }
        loadingDialogList.clear()
    }
}