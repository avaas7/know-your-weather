package com.av.knowYourWeather.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.av.knowYourWeather.Network.ApIConfig
import com.av.knowYourWeather.model.CurrentWeatherResponse
import com.av.knowYourWeather.model.ForecastWeatherResponse
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class WeatherViewModel:ViewModel() {

    private val _currentWeatherDataStateFlow = MutableStateFlow<CurrentWeatherResponse?>(null)
    val currentWeatherDataStateFlow = _currentWeatherDataStateFlow.asStateFlow()

    private val _forecastWeatherDataStateFlow = MutableStateFlow<ForecastWeatherResponse?>(null)
    val forecastWeatherDataStateFlow = _forecastWeatherDataStateFlow.asStateFlow()


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

/*    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError


    var errorMessage: String = ""
        private set
*/

    var auth = Firebase.auth;

    fun userLogOut()
    {
        auth.signOut()
    }


    fun getCurrentWeatherData(city: String) {
        _isLoading.value = true
//        _isError.value = true

     //   val client = ApIConfig.getApiService().getCurrentWeather(city = city, days = 7)
    val client = ApIConfig.getApiService().getCurrentWeather(city = city)

        // Send API request using Retrofit
        client.enqueue(object : Callback<CurrentWeatherResponse> {

            override fun onResponse(
                call: Call<CurrentWeatherResponse>,
                response: Response<CurrentWeatherResponse>
            ) {
                Log.d("av_tag","response recieved")
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                Log.d("av_tag","response success")

                _isLoading.value = false
                _currentWeatherDataStateFlow.value = responseBody
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                t.message?.let { onError(it) }
                t.printStackTrace()
            }

        })
    }

    fun getForecastWeatherData(city: String) {
        _isLoading.value = true
//        _isError.value = true

        //   val client = ApIConfig.getApiService().getCurrentWeather(city = city, days = 7)
        val client = ApIConfig.getApiService().getForecastWeather(city = city, days = 14)

        // Send API request using Retrofit
        client.enqueue(object : Callback<ForecastWeatherResponse> {

            override fun onResponse(
                call: Call<ForecastWeatherResponse>,
                response: Response<ForecastWeatherResponse>
            ) {
                Log.d("av_tag","response recieved")
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                Log.d("av_tag","response success")

                _isLoading.value = false
                _forecastWeatherDataStateFlow.value = responseBody
            }

            override fun onFailure(call: Call<ForecastWeatherResponse>, t: Throwable) {
                t.message?.let { onError(it) }
                t.printStackTrace()
            }
        })
    }

    private fun onError(s: String) {

    }

}