package com.digitalmidges.androidtest101.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.api.DiscoverMovieInfo
import com.digitalmidges.androidtest101.interfaces.IHomeAdapterCallback
import com.digitalmidges.androidtest101.utils.GeneralMethods
import javax.inject.Inject


/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoverMovieInfo>() {

    override fun areItemsTheSame(oldItem: DiscoverMovieInfo, newItem: DiscoverMovieInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DiscoverMovieInfo, newItem: DiscoverMovieInfo): Boolean {
        return oldItem.title == newItem.title
                && oldItem.posterPath == newItem.posterPath
//        return oldItem == newItem
    }

}



class GridAdapter @Inject constructor(
    private val requestManager: RequestManager
) : ListAdapter<DiscoverMovieInfo,RecyclerView.ViewHolder>(DIFF_CALLBACK) {


    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount(): Int {

        if (differ.currentList.isEmpty()){
            return 1 // for loading view
        }

        return differ.currentList.size

    }

    override fun submitList(list: List<DiscoverMovieInfo>?) {
        if (list == differ.currentList) {
            // nothing to do
            return
        }
        differ.submitList(list?.toMutableList())
    }

    private val TAG = "GridAdapter"

    private var isLoading = true
    private var isInPagingMode = false
    private var isErrorWhilePaging = false
//    private var moviesList: ArrayList<DiscoverMovieInfo> = ArrayList()

    private var rowWidth: Int = 0
    private var rowHeight: Int = 0

    val TYPE_LOADING = R.layout.row_home_loading
    private val TYPE_ITEM = R.layout.row_home_grid
//    val TYPE_PAGING = R.layout.row_home_grid_paging

    private var callback: IHomeAdapterCallback? = null

    private var isNoMoreDataToShow = false
    private var totalMoviesNumber = -1

    fun setImageWidthAndHeight(rowWidth: Int, rowHeight: Int) {
        this.rowWidth = rowWidth
        this.rowHeight = rowHeight
    }

    fun setCallback(callback: IHomeAdapterCallback) {
        this.callback = callback
    }


    fun updateData() {
        isLoading = false
    }

    fun updateData(results: ArrayList<DiscoverMovieInfo>) {
        isLoading = false
        isErrorWhilePaging = false
//        removeLastItemIfPagingItem()

//        moviesList.addAll(results)

//        var list : ArrayList<DiscoverMovieInfo> = ArrayList()
//        list.addAll(differ.currentList.toMutableList())
//        list.addAll(results.toMutableList())
        submitList(results)
        checkIfNoMoreDataToLoad()

//        notifyDataSetChanged()
    }

    private fun removeLastItemIfPagingItem() {
        if (differ.currentList.isNotEmpty() && differ.currentList[differ.currentList.lastIndex].isForPagingCell){
            differ.currentList.removeAt(differ.currentList.lastIndex) // remove the paging cell indicator!
        }
    }

    fun onErrorWhileLoading() {

        isLoading = false
        isErrorWhilePaging = true
//        removeLastItemIfPagingItem()

//        notifyDataSetChanged()
    }

/*    fun addPagingCell() {

        val fakeItem =DiscoverMovieInfo()
        fakeItem.isForPagingCell = true

        moviesList.add(fakeItem)
//        submitList(moviesList)
        notifyItemInserted(moviesList.size.minus(1))

    }*/

    fun setTotalMoviesNumber(totalMoviesNumber: Int) {
        this.totalMoviesNumber = totalMoviesNumber
    }


    private fun checkIfNoMoreDataToLoad() {
        if (differ.currentList.isEmpty()) {
            isNoMoreDataToShow = true
            return
        }
        isNoMoreDataToShow = differ.currentList.size == totalMoviesNumber
    }


    override fun getItemViewType(position: Int): Int {


        isInPagingMode = false

        if (isLoading) {
            return TYPE_LOADING
        }

/*        return if (isLastItemAndCanPaging(position)) {
            isCanPaging = true
//            moviesList?.add(DiscoverMovieInfo())
            TYPE_PAGING
        } else TYPE_ITEM*/

    /*    if (moviesList.isNotEmpty() && moviesList[position].isForPagingCell){
            isInPagingMode = true
            return TYPE_PAGING
        }*/


        return TYPE_ITEM

    }

    private fun isLastItemAndCanPaging(position: Int): Boolean {

        if (isInPagingMode || isErrorWhilePaging){
            return false
        }

        return !isNoMoreDataToShow && position == differ.currentList.size - 1 && differ.currentList.size != totalMoviesNumber && totalMoviesNumber > 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        if (viewType == TYPE_LOADING) {
            return LoadingViewHolder(v)
        }
     /*   else if (viewType == TYPE_PAGING) {
            return PagingViewHolder(v)
        }*/

        return ItemViewHolder(v)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ItemViewHolder -> {
                holder.bind(position)
            }
            is LoadingViewHolder -> {
                holder.bind()
            }

//            is PagingViewHolder -> {
//                holder.bind()
//            }
        }
//        if(holder is ItemViewHolder){
//                holder.bind(context)
//        }

    }


/*    override fun getItemCount(): Int {

      *//*  if (isCanPaging) {
            return moviesList?.size?.plus(1) ?: 1
        }*//*

        if (currentList.isEmpty()){
            return 1 // for loading view
        }

        return currentList.size
    }*/




    inner class LoadingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind() {

        }

    }


    inner class PagingViewHolder(v: View?) : RecyclerView.ViewHolder(v!!) {

        fun bind() {
            if (!isInPagingMode) {
                callback?.onPagingScrollingCallback()
            }
        }

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mainContent: ConstraintLayout = view.findViewById(R.id.mainContent)
        var imgPoster: ImageView = view.findViewById(R.id.imgPoster)


        fun bind(position: Int) {

            if (isLastItemAndCanPaging(position)){
                callback?.onPagingScrollingCallback()
            }

            if (differ.currentList.size > position) {


                val item = differ.currentList[position]
                requestManager.load(GeneralMethods.getImagePosterUrl(item)).into(imgPoster)

                imgPoster.setOnClickListener {
                    callback?.onRowClick(item)
                }


            }


        }

    }


}