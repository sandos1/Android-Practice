package com.example.weatherapp.retrofit_api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    @Volatile
    private var RETROFIT_INSTANCE: Retrofit? = null
    const val BASE_URL = "https://weather.exam.bottlerocketservices.com/"

   private fun getClient() : Retrofit {
        return RETROFIT_INSTANCE ?: synchronized(this) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            RETROFIT_INSTANCE = retrofit
            retrofit
        }
    }

    //Retrofit service generator
    public fun<T> getWeatherService(service: Class<T>): T {
        return getClient().create(service)
    }
}