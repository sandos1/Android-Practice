package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.RecyclerAdapter
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Root
import com.example.weatherapp.model.subquery.Day
import com.example.weatherapp.model.subquery.QueryBase
import com.example.weatherapp.model.subquery.Weather
import com.example.weatherapp.retrofit_api.ApiInterface
import com.example.weatherapp.retrofit_api.RetrofitClient
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
   // lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        getCityDetail("Florence")
        city.text="Florence"

        //query music by artistName on user search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // do your logic here
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    getCityDetail(newText)
                }
                return false
            }
        })

        //getCityDetail();
    }

    fun getCityDetail(cityName: String) {

        CoroutineScope(Dispatchers.IO).launch{
            var  weatherDetail: ApiInterface = RetrofitClient.getWeatherService(ApiInterface::class.java);
            val cityDetail:Response<Root> =  weatherDetail.getCityWeatherDetail(cityName)

            if(cityDetail.isSuccessful){

                runOnUiThread(Runnable {
                    city.text = cityName
                })
                cityDetail.body()?.let {

                    val citiesList:List<City>?= it.cities

                    citiesList.let {

                        if (it != null) {
                            for (d in it){
                                val timeZone:String? = d.timezone;
                                val date = Date()
                                date.time
                                val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                df.timeZone = TimeZone.getTimeZone(timeZone)

                                runOnUiThread(Runnable {
                                    mtime.text = getCurrentTime(timeZone)
                                    mdate.text = getTodayDate(timeZone)
                                    dayOfWeek.text = getTodaysDay(timeZone)

                                })

                                Log.d("TAG", "time: " + df.format(date.time))

                                //if city name exist make another API call to fetch weather detail
                                val geonameId:Int = d.geonameid
                                getWeatherQueryDetail(geonameId)
                            }
                        }
            }

            Log.d("TAG", "getCityDetail: " + it.cities)
            Log.d("TAG", "getCityDetail: " + it.totalCitiesFound)
        }
            }else{

                        Toast.makeText(applicationContext, "City not Available", Toast.LENGTH_LONG).show()

                        Log.d("TAG", "Fail")

            }

                }

    }

    fun getTodaysDay(timeZone: String?): String? {
        val date = Date()
        /* Specifying the format */
        val requiredFormat: DateFormat = SimpleDateFormat("EEEEEE")
        /* Setting the Timezone */requiredFormat.timeZone = TimeZone.getTimeZone(timeZone)
        /* Picking the day value in the required Format */
        return requiredFormat.format(date).toUpperCase()
    }
    fun getCurrentTime(timeZone: String?): String? {
        /* Specifying the format */
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm:ss")
        /* Setting the Timezone */
        val cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone))
        dateFormat.timeZone = cal.timeZone
        /* Picking the time value in the required Format */
        return dateFormat.format(cal.time)
    }

    fun getTodayDate(timeZone: String?): String? {
        val todayDate = Date()
        /* Specifying the format */
        val todayDateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        /* Setting the Timezone */
        todayDateFormat.timeZone = TimeZone.getTimeZone(timeZone)
        /* Picking the date value in the required Format */
        return todayDateFormat.format(todayDate)
    }

    suspend fun getWeatherQueryDetail(nameId: Int){

        var  weatherDetail: ApiInterface = RetrofitClient.getWeatherService(ApiInterface::class.java);
        val weatherQueryDetail:Response<QueryBase> =  weatherDetail.getCityDetailById(nameId)

        if(weatherQueryDetail.isSuccessful){

            //base object
            val queryResult:QueryBase? = weatherQueryDetail.body()

            //retrieve weather object
            val weatherObject:Weather? = queryResult?.weather
            //retrieve List object of Day from weatherObject
            val weatherDayList: List<Day>? = weatherObject?.days

            //loop through List of weather day to retrieve data
            if (weatherDayList != null) {
                for(detail in weatherDayList){
                    val day:Day= detail

                    //List of weather data
                    val hourlyWeatherLIst = day.hourlyWeather

                    //set List for RecyclerAdapter

                    recyclerAdapter = RecyclerAdapter(this@MainActivity, hourlyWeatherLIst)
                    runOnUiThread(Runnable {
                        temperature.text = hourlyWeatherLIst.get(1).temperature.toString() + "°"
                        recyclerView.adapter = recyclerAdapter
                    })



                       // temperature.text=data.temperature.toString()

                }
            }
        }else{

            Toast.makeText(applicationContext, "City not Available", Toast.LENGTH_LONG).show()

            Log.d("TAG", "Fail")

        }



    }
}