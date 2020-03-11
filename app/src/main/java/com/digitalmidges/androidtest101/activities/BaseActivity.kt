package com.digitalmidges.androidtest101.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
abstract class BaseActivity : AppCompatActivity(){


    protected abstract fun setRootView(): View

    protected abstract fun beforeInit()

    protected abstract fun initActivity()

    protected abstract fun afterInit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initBaseActivity()
        // abstract
        setContentView(setRootView())
        // abstract
        beforeInit()
        // abstract
        initActivity()
        // abstract
        afterInit()
    }

    private fun initBaseActivity() {


    }

}