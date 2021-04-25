package com.example.weatherapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.RecyclerAdapter
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Root
import com.example.weatherapp.model.subquery.Day
import com.example.weatherapp.model.subquery.QueryBase
import com.example.weatherapp.model.subquery.Weather
import com.example.weatherapp.mvvm.Repository
import com.example.weatherapp.mvvm.WeatherViewModel
import com.example.weatherapp.mvvm.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), SearchCityFragment.SelectedCityName {
   // lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var repository: Repository
    lateinit var viewModelFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setSupportActionBar(toolbar)
        recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)

        repository = Repository(this)
        viewModelFactory = WeatherViewModelFactory(repository)
        viewModel =  ViewModelProvider(this,viewModelFactory).get(WeatherViewModel::class.java)



        if(checkNetWorkConnectivity()){
            getCityDetail("Florence")
            city.text = "Florence"
        }else{

            Toast.makeText(applicationContext,"No Internet connection",Toast.LENGTH_LONG).show()
        }


        search_bar_Btn.setOnClickListener{
            if(checkNetWorkConnectivity()){
                openSearchFragment()
            }else{
                Toast.makeText(applicationContext,"No Internet connection",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getCityDetail(cityName: String) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                search_bar_Btn.isEnabled=true
                val cityDetail: Response<Root> = viewModel.getWeatherDetail(cityName)
                if (cityDetail.isSuccessful) {
                    runOnUiThread(Runnable {
                        city.text = cityName
                        progressBar.visibility = View.GONE
                    })
                    cityDetail.body()?.let {
                        val citiesList: List<City>? = it.cities
                        citiesList.let {
                            if (it != null) {
                                for (d in it) {
                                    val timeZone: String? = d.timezone;
                                    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    df.timeZone = TimeZone.getTimeZone(timeZone)
                                    runOnUiThread(Runnable {
                                        mtime.text = getCurrentTime(timeZone)
                                        mdate.text = getTodayDate(timeZone)
                                        dayOfWeek.text = getTodaysDay(timeZone)
                                    })
                                    //if city name exist make another API call to fetch weather detail
                                    val geonameId: Int = d.geonameid
                                    getWeatherQueryDetail(geonameId)
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "City not Available", Toast.LENGTH_LONG)
                            .show()
                    Log.d("TAG", "Fail")
                }
            } catch (exception: IOException) {

                withContext(Dispatchers.Main){
                    netWorkError()
                    Toast.makeText(applicationContext,"No Internet connection",Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                openSearchFragment()
                true
            }
            R.id.delete_city->{
                deleteCurrentCity()
                true
            }
            R.id.map_radar -> {
                Toast.makeText(this@MainActivity, "To be implemented!", Toast.LENGTH_SHORT).show()
                true
            }
            else-> false
        }
    }
    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchCityFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun deleteCurrentCity() {
        Toast.makeText(this, "Current city deleted", Toast.LENGTH_SHORT).show()
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
        val dateFormat: DateFormat = SimpleDateFormat("hh.mm aa")
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

        try{
        val weatherQueryDetail:Response<QueryBase> =  viewModel.getCityDetailById(nameId)

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
                        temp.text = hourlyWeatherLIst.get(0).temperature.toString()+ "°"
                        Ttemp.text = hourlyWeatherLIst.get(1).temperature.toString()+ "°"
                        Wtemp.text = hourlyWeatherLIst.get(2).temperature.toString()+ "°"
                        thtemp.text = hourlyWeatherLIst.get(3).temperature.toString()+ "°"
                        Ftemp.text = hourlyWeatherLIst.get(4).temperature.toString()+ "°"
                        Stemp.text = hourlyWeatherLIst.get(5).temperature.toString()+ "°"
                        Suntemp.text = hourlyWeatherLIst.get(6).temperature.toString()+ "°"
                        recyclerView.adapter = recyclerAdapter
                    })

                }
            }
        }else{

            Toast.makeText(applicationContext, "City not Available", Toast.LENGTH_LONG).show()

        }
        } catch (exception: IOException) {

            withContext(Dispatchers.Main){
                Toast.makeText(applicationContext,"No Internet connection",Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun setName(cityName: String) {
        getCityDetail(cityName)
    }
    //check if the device is connected to internet or mobile data
    fun checkNetWorkConnectivity():Boolean{
        val connectivityManager:ConnectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        if(netWorkInfo !=null && netWorkInfo.isConnected()){
            return true
        }
        return false
    }


    fun netWorkError(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.alert_dialog)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.btn_try.setOnClickListener{
            //recreate button
            recreate()
        }
        dialog.show()
    }
}