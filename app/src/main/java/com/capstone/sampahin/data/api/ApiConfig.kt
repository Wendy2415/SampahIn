package com.capstone.sampahin.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private fun createRetrofit(baseUrl: String): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApiService(): ApiService {
        val retrofit = createRetrofit("https://firebase-851479113294.asia-southeast2.run.app/")
        return retrofit.create(ApiService::class.java)
    }

    fun getMLApiService(): MLApiService {
        val retrofit = createRetrofit("https://backend-ml-2-dot-sampahin.et.r.appspot.com/")
        return retrofit.create(MLApiService::class.java)
    }
}

