package com.digitalmidges.androidtest101.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.digitalmidges.androidtest101.R
import com.digitalmidges.androidtest101.api.MoviesApi
import com.digitalmidges.androidtest101.db.MoviesDao
import com.digitalmidges.androidtest101.db.MoviesDatabase
import com.digitalmidges.androidtest101.utils.BASE_URL
import com.digitalmidges.androidtest101.utils.LiveDataCallAdapterFactory
import com.google.gson.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideMoviesApi(): MoviesApi {

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date?> {
            var df: DateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)

            @Throws(JsonParseException::class)
            override fun deserialize(
                json: JsonElement,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): Date? {
                return try {
                    df.parse(json.asString)
                } catch (e: ParseException) {
                    null
                }
            }
        })
        val gson = gsonBuilder.create() // fix the bug when date is empty or null

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): MoviesDatabase {
        return Room
            .databaseBuilder(app, MoviesDatabase::class.java, "movies.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao {
        return db.moviesDao()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.loading_place_holder)
            .error(R.drawable.error_place_holder)
            .centerCrop()
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }


    @Singleton
    @Provides
    fun isTablet(application: Application): Boolean {
        return application.resources.getBoolean(R.bool.isTablet)
    }


}
