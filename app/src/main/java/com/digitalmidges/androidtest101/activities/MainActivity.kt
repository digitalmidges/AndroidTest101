package com.digitalmidges.androidtest101.activities

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.databinding.ActivityMainBinding
import com.digitalmidges.androidtest101.enums.EToolbarStyleType
import com.digitalmidges.androidtest101.utils.GeneralMethods
import com.digitalmidges.androidtest101.viewModels.HomeViewModel
import com.digitalmidges.androidtest101.viewModels.MainViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
class MainActivity : BaseActivity(), HasAndroidInjector {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val TAG = "MainActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @JvmField // expose a field
    @Inject
    var isTablet: Boolean = false

    private val mainViewModel: MainViewModel by viewModels{
        viewModelFactory
    }

    override fun androidInjector(): AndroidInjector<Any> =dispatchingAndroidInjector

    override fun setRootView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun beforeInit() {

    }

    override fun initActivity() {

        navController = findNavController(R.id.nav_host_fragment)
//        setContentView(binding.root)

//        var dialog = LoadingDialog.newInstance()
//        dialog.show(supportFragmentManager,"dialog")


        if (GeneralMethods.isInternetAvailable(this)){
//            Toast.makeText(this, "WE HAVE INTERNET",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "NO INTERNET !!!",Toast.LENGTH_LONG).show()
        }

    }


    override fun afterInit() {

//        homeViewModel.postMoviePage(1) // fetch the movies
        initToolbar()
        subscribeToViewModel()

    }

    private fun subscribeToViewModel() {

        mainViewModel.toolbarStyle.observe(this, Observer {
            setToolbarStyle(it)
//            Toast.makeText(this, "The style is: $it",Toast.LENGTH_LONG).show()
        })

        mainViewModel.toolbarText.observe(this, Observer {
            setToolbarText(it)
        })




    }

    private fun setToolbarText(it: String?) {
        if (it != null) {
            binding.toolbar.tvToolbarTitle.text = it
        }

    }
    private fun setToolbarStyle(toolbarStyle: EToolbarStyleType) {
        if (isTablet){
            doToolbarHomeStyle()
        }else {
            when (toolbarStyle) {

                EToolbarStyleType.TOOLBAR_STYLE_HOME -> {
                    doToolbarHomeStyle()
                }
                EToolbarStyleType.TOOLBAR_STYLE_BACK_BUTTON -> {
                    doToolbarBackButtonStyle()
                }

                else -> {
                    doToolbarHomeStyle()
                }

            }
        }

    }

    private fun doToolbarBackButtonStyle() {
        binding.toolbar.imgToolbarBack.visibility = View.VISIBLE
    }

    private fun doToolbarHomeStyle() {
        binding.toolbar.imgToolbarBack.visibility = View.GONE
        binding.toolbar.tvToolbarTitle.text = getString(R.string.app_name)

    }


    private fun initToolbar() {

        binding.toolbar.imgToolbarBack.setOnClickListener {
//            navController.popBackStack(R.id.movieInfoFragment,true)
            onBackPressed()
        }


    }
}