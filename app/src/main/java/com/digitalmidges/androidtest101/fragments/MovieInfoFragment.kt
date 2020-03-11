package com.digitalmidges.androidtest101.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.adapters.TrailersAdapter
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.api.MovieVideosItem
import com.digitalmidges.androidtest101.databinding.FragmentMovieInfoBinding
import com.digitalmidges.androidtest101.interfaces.ITrailersAdapterCallback
import com.digitalmidges.androidtest101.utils.GeneralMethods
import com.digitalmidges.androidtest101.utils.MOVIE_ID
import com.digitalmidges.androidtest101.utils.Status
import com.digitalmidges.androidtest101.viewModels.MainViewModel
import com.digitalmidges.androidtest101.viewModels.MovieInfoViewModel
import javax.inject.Inject


/**
 * Created with care by odedfunt on 08/03/2020.
 *
 * TODO: Add a class header comment!
 */
class MovieInfoFragment : BaseFragment() {


    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @JvmField // expose a field
    @Inject
    var isTablet: Boolean = false

    private val movieInfoViewModel: MovieInfoViewModel by activityViewModels {
        viewModelFactory
    }

    private val mainViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var trailersAdapter: TrailersAdapter

    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun getBundle(bundle: Bundle?) {

        if (bundle != null) {
            movieId = bundle.getInt(MOVIE_ID, -1)
        }

    }

    override fun initFragment(v: View) {
    }

    override fun setDefaultViewsBehavior() {

        mainViewModel.setToolbarStyleBackButton()
        mainViewModel.setToolbarText(getString(R.string.movie_details))
    }

    override fun onFragmentInitializeReady() {

        initRecycleViews()
        subscribeToViewModel()

        if ((movieId==null || movieId!! <0) && isTablet){
            // in tablet mode - the user didn't pick anything yet
            binding.groupInfo.visibility = View.GONE
            binding.lottieAnimation.visibility = View.VISIBLE
        }else{
            binding.groupInfo.visibility = View.VISIBLE
            binding.lottieAnimation.visibility = View.GONE
            binding.lottieAnimation.pauseAnimation()
        }

    }

    private fun subscribeToViewModel() {

        movieInfoViewModel.getMovieById(movieId)


        movieInfoViewModel.movieInfo.observe(this, Observer {
            if (it!=null) {
                setData(it)
            }
        })


        movieInfoViewModel.getMovieVideosList(movieId)
        movieInfoViewModel.movieVideosList.observe(viewLifecycleOwner, Observer {
            if (it!=null && it.status == Status.SUCCESS && it.data!=null) {
                trailersAdapter.setData(it.data.results)
            }
        })


//        movieInfoViewModel.youtubeIntent.observe(this, Observer {
//            startActivity(it)
//        })

    }

    private fun setData(movieInfo: DiscoverMovieInfo) {


        binding.tvMovieName.text = movieInfo.title
        binding.tvMovieDescription.text = movieInfo.overview
        binding.tvMovieRating.text =
            getString(R.string.rating_place_holder, movieInfo.voteAverage.toString())

        val movieReleaseDateYear = GeneralMethods.getMovieReleaseDateYear(movieInfo) ?: ""
        binding.tvMoviePublishYear.text = movieReleaseDateYear

        requestManager.load(GeneralMethods.getImagePosterUrl(movieInfo))
            .into(binding.imgMoviePoster)

    }

    private fun initRecycleViews() {

        trailersAdapter = TrailersAdapter(activity)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = trailersAdapter

        trailersAdapter.setCallback(object : ITrailersAdapterCallback {
            override fun onRowClick(item: MovieVideosItem) {
                movieInfoViewModel.openYouTube(item.key)
//                openYouTube(item.key)

            }

        })
    }





    override fun setViewsClickListeners() {

        binding.btnButton.setOnClickListener {
            Toast.makeText(activity, "What is my purpose!?", Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}