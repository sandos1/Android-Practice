package com.example.weatherapp.model.subquery

class Day(

     val high:Int?=null,
     val  weatherType:String,
     val hourlyWeather:List<HourlyWeather>,
     val  low:Int,
    val  dayOfTheWeek:Int
    ) {

}
