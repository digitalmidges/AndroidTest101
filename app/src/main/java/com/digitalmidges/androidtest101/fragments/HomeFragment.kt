package com.digitalmidges.androidtest101.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.adapters.GridAdapter
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.databinding.FragmentHomeBinding
import com.digitalmidges.androidtest101.di.Injectable
import com.digitalmidges.androidtest101.interfaces.IHomeAdapterCallback
import com.digitalmidges.androidtest101.utils.MOVIE_ID
import com.digitalmidges.androidtest101.utils.Status
import com.digitalmidges.androidtest101.viewModels.HomeViewModel
import com.digitalmidges.androidtest101.viewModels.MainViewModel
import javax.inject.Inject

/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
class HomeFragment : BaseFragment(), Injectable {

    private lateinit var navController: NavController

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var gridAdapter: GridAdapter

    @JvmField // expose a field
    @Inject
    var isTablet: Boolean = false


    private val TAG = "HomeFragment"

    private val homeViewModel: HomeViewModel by activityViewModels {
        viewModelFactory
    }

    private val mainViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private var numberOfItemsPerLine = 3


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun getBundle(bundle: Bundle?) {
    }

    override fun initFragment(v: View) {
        navController = Navigation.findNavController(v)
    }

    override fun setDefaultViewsBehavior() {
        numberOfItemsPerLine = resources.getInteger(R.integer.grid_number_of_items_per_line)
    }

    override fun onFragmentInitializeReady() {

//        homeViewModel.getMoviesDataByPage(1) // get the movies

        initRecycleViews()
        subscribeToLiveData() // subscribe to view model only after the recycler view init

    }


    override fun setViewsClickListeners() {


    }


    private fun initRecycleViews() {
//        binding.recyclerView.post { continueInitRecycleViews(binding.recyclerView.width) }

//        val finalWidth = calcRecycleViewWidthSize(screenWidth, numberOfItemsPerLine, R.dimen.grid_margin)
//        val height = finalWidth * 1.2

        val gridLayoutManager = GridLayoutManager(activity, numberOfItemsPerLine)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = gridAdapter
        binding.recyclerView.itemAnimator = null
//        binding.recyclerView.setHasFixedSize(true);
//        gridAdapter.setImageWidthAndHeight(finalWidth,height.toInt())
        gridAdapter.setCallback(object : IHomeAdapterCallback {
            override fun onRowClick(item: DiscoverMovieInfo) {

                doOnGridRowClick(item)

            }

            override fun onPagingScrollingCallback() {
//                binding.recyclerView.post { gridAdapter.addPagingCell() } // add the spinner cell to indicate the user that loading is under way

                homeViewModel.getMoviesDataByPage() // get more data
            }

        })

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (gridAdapter.getItemViewType(position)) {
                    gridAdapter.TYPE_LOADING/*, gridAdapter.TYPE_PAGING*/-> numberOfItemsPerLine
                    else -> 1
                }
            }
        }


    }

    private fun doOnGridRowClick(item: DiscoverMovieInfo) {

        val bundle = Bundle()
        item.id?.let { bundle.putInt(MOVIE_ID, it) }

        if (isTablet) {
            Navigation.findNavController(activity!!,R.id.nav_host_fragment_tablet).navigate(R.id.movieInfoFragment, bundle);
        } else {
            navController.navigate(R.id.action_homeFragment_to_movieInfoFragment, bundle)
        }


    }

    private fun subscribeToLiveData() {


        homeViewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null ){

                when (it.status){

                    Status.SUCCESS->{
                        if (it.data != null){



//                            gridAdapter.updateData(it.data as ArrayList<DiscoverMovieInfo>)
                            val list :ArrayList<DiscoverMovieInfo> = it.data as ArrayList<DiscoverMovieInfo>
//                            gridAdapter.submitList(list.toMutableList())

                            val totalResults = list[list.lastIndex].totalResults?: 50

                            val fullList = homeViewModel.getAllMoviesList()

                            val page = list[list.lastIndex].page
                            if (fullList!=null && fullList.isNotEmpty() && page == fullList[fullList.lastIndex].page){
                                // work around, prevent duplicates
                                Log.d(TAG, "subscribeToLiveData: ")
                            }else{
                                homeViewModel.addDataToMoviesList(list)
                            }
                            gridAdapter.setTotalMoviesNumber(totalResults) // for the paging
                            gridAdapter.updateData(fullList)

                        }
                    }

                    Status.ERROR->{
//                        gridAdapter.onErrorWhileLoading() // cancel the loading when an error
                    }

                }

            }
        })



        homeViewModel.movieListState.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(it) // restore the state of the recycler view since nav controller destroy the fragment when navigate
        })

    }


    private fun calcRecycleViewWidthSize(
        width: Int,
        numberOfItemsPerLines: Int,
        @DimenRes dividerSize: Int
    ): Int {
        var width = width
        width -= resources.getDimensionPixelSize(dividerSize) * numberOfItemsPerLines
        return width / numberOfItemsPerLines
    }


    override fun onResume() {
        super.onResume()
        mainViewModel.setToolbarStyleHome()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        homeViewModel.saveListState(binding.recyclerView.layoutManager?.onSaveInstanceState()) // save the state of the recycler view since nav controller destroy the fragment when navigate

        _binding = null

    }
}