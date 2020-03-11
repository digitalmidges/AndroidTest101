package com.digitalmidges.androidtest101.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.api.MovieVideosItem
import com.digitalmidges.androidtest101.interfaces.ITrailersAdapterCallback

/**
 * Created with care by odedfunt on 09/03/2020.
 *
 * TODO: Add a class header comment!
 */
class TrailersAdapter(var context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM = R.layout.row_movie_trailer

    private var callback: ITrailersAdapterCallback? = null

    private var videoList: ArrayList<MovieVideosItem>? = null

    fun setCallback(callback: ITrailersAdapterCallback) {
        this.callback = callback
    }

    fun setData(results: ArrayList<MovieVideosItem>?) {
        videoList = results
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            holder.bind(position)
        }

    }


    override fun getItemCount(): Int {
        return videoList?.size ?: 0
    }


    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvSubTitle: TextView = view.findViewById(R.id.tvSubTitle)

        fun bind(position: Int) {

            if (videoList != null) {

                val item = videoList!![position]

                tvTitle.text = item.name
                tvSubTitle.text = item.type

                itemView.setOnClickListener {
                    callback?.onRowClick(item)
                }
            }
        }

    }

}