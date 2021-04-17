package com.example.weatherapp.model.subquery

import com.example.weatherapp.model.City
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QueryBase(
                @SerializedName("weather")
                @Expose
                val  weather:Weather?=null,
                @SerializedName("city")
                @Expose
                val  city: City
) {
}