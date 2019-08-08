package com.appknot.seotda.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appknot.seotda.rx.AutoClearedDisposable

/**
 *
 * @author Jin on 2019-06-12
 */
open class BaseActivity : AppCompatActivity()    {
    
    internal val disposables = AutoClearedDisposable(this)

    internal val viewDisposables = AutoClearedDisposable(lifecycleOwner = this, alwaysClearOnStop = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}