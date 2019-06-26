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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}