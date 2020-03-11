package com.digitalmidges.androidtest101.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.digitalmidges.androidtest101.di.Injectable

/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
abstract class BaseFragment: Fragment(), Injectable {



    protected abstract fun getBundle(bundle: Bundle?)

    protected abstract fun initFragment(v: View)

    protected abstract fun setDefaultViewsBehavior()

    protected abstract fun onFragmentInitializeReady()

    protected abstract fun setViewsClickListeners()


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return setRootView()
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getBundle(this.arguments) // abstract - use int the child
        initFragment(view) // abstract - use int the child
        setDefaultViewsBehavior() // abstract - use int the child
        onFragmentInitializeReady() // abstract - use int the child
        setViewsClickListeners() // abstract - use int the child
    }

}