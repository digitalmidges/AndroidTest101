package com.digitalmidges.androidtest101.db

import androidx.room.TypeConverter
import com.digitalmidges.androidtest101.api.MovieVideosItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


object VideosTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): ArrayList<MovieVideosItem> {
        val listType: Type =
            object : TypeToken<ArrayList<MovieVideosItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(list: ArrayList<MovieVideosItem>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}
