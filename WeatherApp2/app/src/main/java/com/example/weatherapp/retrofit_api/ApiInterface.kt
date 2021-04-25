package com.example.weatherapp.retrofit_api

import androidx.lifecycle.LiveData
import com.example.weatherapp.model.Root
import com.example.weatherapp.model.subquery.QueryBase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET("cities")
    suspend fun getAllCityName(): Response<Root>

    @GET("cities")
     suspend fun getCityWeatherDetail( @Query("search") name:String): Response<Root>

    @GET("cities/{geonameid}")
    suspend fun getCityDetailById(@Path("geonameid") geonameId:Int):Response<QueryBase>
}