package com.example.weatherapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment() {

    private lateinit var cityName: String
    private lateinit var cityTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var feelsLikeTextView: TextView
    private lateinit var weatherApi: WeatherApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        // Initialize views
        cityTextView = view.findViewById(R.id.cityTextView)
        temperatureTextView = view.findViewById(R.id.temperatureTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        humidityTextView = view.findViewById(R.id.humidityTextView)
        feelsLikeTextView = view.findViewById(R.id.feelsLikeTextView)

        // Retrieve city name from the arguments passed
        arguments?.getString("cityName")?.let {
            cityName = it
            cityTextView.text = "City: $cityName"
        }

        // Initialize Retrofit API
        weatherApi = RetrofitInstance.api

        // Fetch weather data
        fetchWeatherData(cityName)

        return view
    }

    private fun fetchWeatherData(city: String) {
        weatherApi.getCurrentWeather(city, "ac78393ca80d989abc96a266ea390155").enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        temperatureTextView.text = "Temperature: ${it.main.temp}°C"
                        descriptionTextView.text = "Description: ${it.weather[0].description}"
                        humidityTextView.text = "Humidity: ${it.main.humidity}%"
                        feelsLikeTextView.text = "Feels Like: ${it.main.feels_like}°C"
                    }
                } else {
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch weather", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
