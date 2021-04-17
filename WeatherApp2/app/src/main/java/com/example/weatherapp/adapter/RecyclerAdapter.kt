package com.example.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.subquery.HourlyWeather

class RecyclerAdapter(val context: Context,val hourlyWeatherList: List<HourlyWeather>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {

        val view:View = LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       val weatherInfo:HourlyWeather = hourlyWeatherList[position]

        if(weatherInfo.hour!! in 1..12){
            holder.time.text=weatherInfo.hour.toString()+"AM"
        }else{
            holder.time.text=weatherInfo.hour.toString()+"PM"
        }

        holder.temperature.text=weatherInfo.temperature.toString()+"°"
        val chanceOfRain = weatherInfo.rainChance?.times(100)
        holder.chanceOfRain.text=chanceOfRain.toString()+"%"
        holder.wind.text=weatherInfo.windSpeed.toString()
        val humidity = weatherInfo.humidity?.times(100)
        holder.humidity.text = humidity.toString()+"%"
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var imageView:ImageView
        lateinit var time:TextView
        lateinit var temperature:TextView
        lateinit var chanceOfRain:TextView
        lateinit var wind:TextView
        lateinit var humidity:TextView
        init {

            imageView = itemView.findViewById(R.id.weatherIcon);
            time = itemView.findViewById(R.id.time)
            temperature =itemView.findViewById(R.id.temp)
            chanceOfRain =itemView.findViewById(R.id.chanceOfRain)
            wind =itemView.findViewById(R.id.wind)
            humidity =itemView.findViewById(R.id.humidity)

        }


    }
}