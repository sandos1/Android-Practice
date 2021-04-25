package com.example.weatherapp.mvvm

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Root
import com.example.weatherapp.model.subquery.QueryBase
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class WeatherViewModel(val repository: Repository): ViewModel() {

     val allCityName: MutableLiveData<List<String>> = MutableLiveData()


    init {
        getAllCityName()
    }
    fun getallCityName():MutableLiveData<List<String>>{
        return allCityName
    }

     private fun getAllCityName(){
         viewModelScope.launch(Dispatchers.IO) {
             try {
             var listOfAllCity:MutableList<String> = mutableListOf()
             val weatherRootResult:Response<Root> = repository.getAllCityName()
             if (weatherRootResult.isSuccessful){
                 val weatherResponse: Root? = weatherRootResult.body()
                 val cityNameList: List<City>? = weatherResponse?.cities
                 if (cityNameList != null) {
                     for (city in cityNameList){
                         city.name?.let { listOfAllCity.add(it) }
                     }
                 }
                 allCityName.postValue(listOfAllCity)
             }
             } catch (exception: IOException) {

                 Log.d("Internet", "No Internet connection")
             }

    }
     }

    suspend fun getWeatherDetail(city:String):Response<Root>{
           return repository.getCityWeatherDetail(city)
    }

     suspend fun getCityDetailById(geonameId:Int):Response<QueryBase>{
             return repository.getCityDetailById(geonameId)
    }

}