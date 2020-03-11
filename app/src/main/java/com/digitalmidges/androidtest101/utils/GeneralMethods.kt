package com.digitalmidges.androidtest101.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created with care by odedfunt on 10/03/2020.
 *
 * TODO: Add a class header comment!
 */
class GeneralMethods {
    companion object {

        @JvmStatic
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
            val networks = connectivityManager.allNetworks
            var hasInternet = false
            if (networks.isNotEmpty()) {
                for (network in networks) {
                    val nc = connectivityManager.getNetworkCapabilities(network)
                    if (nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) hasInternet =
                        true
                }
            }
            return hasInternet
        }

        @JvmStatic
        fun getImagePosterUrl(movieInfo: DiscoverMovieInfo):String{
            return IMAGE_BASE_URL +movieInfo.posterPath
        }

        @JvmStatic
        fun getMovieReleaseDateYear(movieInfo: DiscoverMovieInfo):String?{

            if (movieInfo.releaseDate != null) {
                val dateFormatter = SimpleDateFormat("yyyy", Locale.ENGLISH)

                return dateFormatter.format(movieInfo.releaseDate!!)
            }
            return null
        }


    }

}