package com.digitalmidges.androidtest101.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


object MoviesTypeConverters {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }

 /*   @TypeConverter
    @JvmStatic
    fun stringToDate(value: String?): Date? {
        return value?.let {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(it)
            return date

        }
    }*/

    @TypeConverter
    @JvmStatic
    fun fromDate(iDate: Date?): Long {
        return iDate?.time ?: 0
    }

    @TypeConverter
    @JvmStatic
    fun toDate(iTime: Long): Date? {
        return if (iTime == 0L) null else Date(iTime)
    }



}
