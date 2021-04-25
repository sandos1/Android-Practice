package com.example.weatherapp.mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Root
import com.example.weatherapp.model.subquery.QueryBase
import com.example.weatherapp.retrofit_api.ApiInterface
import com.example.weatherapp.retrofit_api.RetrofitClient
import retrofit2.Response
import retrofit2.Retrofit

class Repository(val mcontext:Context) {
    val retrofit= RetrofitClient


    val apiInterface = retrofit.getWeatherService(ApiInterface::class.java,mcontext)


    suspend fun getCityWeatherDetail(city: String):Response<Root>{

        return apiInterface.getCityWeatherDetail(city)
    }

    suspend fun getCityDetailById(geonameId: Int):Response<QueryBase>{
        return apiInterface.getCityDetailById(geonameId)
    }

    suspend fun getAllCityName():Response<Root>{
        return apiInterface.getAllCityName()
    }







}