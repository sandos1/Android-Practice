package com.example.weatherapp

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Root
import com.example.weatherapp.mvvm.Repository
import com.example.weatherapp.mvvm.WeatherViewModel
import com.example.weatherapp.mvvm.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.fragment_search_city.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class SearchCityFragment : Fragment() {

    interface SelectedCityName{
        fun setName(cityName: String)
    }

    lateinit var selectedCityName:SelectedCityName
    lateinit var repository: Repository
    lateinit var viewModelFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    lateinit var listOfAllCity:List<String>
    lateinit var  adapter:ArrayAdapter<String>


    override fun onAttach(context: Context) {
        super.onAttach(context)
        selectedCityName = context as SelectedCityName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Repository(this.requireActivity())
        viewModelFactory = WeatherViewModelFactory(repository)
        viewModel =  ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        listOfAllCity= emptyList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =inflater.inflate(R.layout.fragment_search_city, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        close_search_icon.setOnClickListener{
            closeFragment()
        }

        viewModel.getallCityName().observe(this, Observer {
            //initialise listOfAllCity
            listOfAllCity = it

            if(listOfAllCity!=null){
                //return the list of all the City name and provide them as sugestion
                adapter = activity?.let { it1 ->
                    ArrayAdapter<String>(
                            it1,
                            android.R.layout.simple_list_item_1,
                            listOfAllCity
                    )
                }!!
                search_city.setAdapter(adapter)
                search_city.setThreshold(1)
            }else{
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
            }

        })

        //on key event action enter,search
        search_city.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_FLAG_NO_ENTER_ACTION || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val input: String = search_city.text.toString()
                if (!TextUtils.isEmpty(input)) {


                    //pass user input to Activity to display city name info
                    //check if user input contain in  listOfAllCity.
                    if (listOfAllCity.contains(input)) {
                        selectedCityName.setName(input)
                        closeFragment()
                    } else {
                        Toast.makeText(activity, "City does not exist", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity, "form must not be empty", Toast.LENGTH_LONG).show()
                }
            }
            false
            }
        )
    }

    fun closeFragment(){
        val manager = requireActivity().supportFragmentManager
        manager.beginTransaction().remove(this).commit()
    }
}