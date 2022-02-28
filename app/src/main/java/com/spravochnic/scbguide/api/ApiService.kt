package com.spravochnic.scbguide.api

import android.annotation.SuppressLint
import androidx.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.spravochnic.scbguide.api.response.DetailsCategoriesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import com.spravochnic.scbguide.BuildConfig
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("instruments/getCategoriesLectory.php")
    suspend fun getDetailCategoriesLectory(): DetailsCategoriesResponse

    companion object {

        const val baseUrl = "http://ovz1.sergei-pokhodai.mzlgn.vps.myjino.ru/"

        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val API: ApiService by lazy {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.callTimeout(60, TimeUnit.SECONDS)
            okHttpClient.connectTimeout(20, TimeUnit.SECONDS)
            okHttpClient.readTimeout(20, TimeUnit.SECONDS)
            okHttpClient.writeTimeout(20, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    okHttpClient.addInterceptor(this)
                }
            }
            val retrofit = Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }
}