package com.example.weatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


 class Root(
    @SerializedName("startIndex")
    @Expose
    var startIndex:Int?= null,

     @SerializedName("totalCitiesFound")
     @Expose
    var totalCitiesFound :Int? = null,

    @SerializedName("cities")
    var cities: List<City>? = null){}
