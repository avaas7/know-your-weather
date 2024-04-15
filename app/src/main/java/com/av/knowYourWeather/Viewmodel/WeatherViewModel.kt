package com.av.knowYourWeather.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.knowYourWeather.Network.ApIConfig
import com.av.knowYourWeather.Repository.WeatherRepository
import com.av.knowYourWeather.model.CurrentWeatherResponse
import com.av.knowYourWeather.model.ForecastWeatherResponse
import com.av.knowYourWeather.model.Result
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var weatherRepository: WeatherRepository) : ViewModel() {

    private val _currentWeatherDataStateFlow = MutableStateFlow<CurrentWeatherResponse?>(null)
    val currentWeatherDataStateFlow = _currentWeatherDataStateFlow.asStateFlow()

    private val _forecastWeatherDataStateFlow = MutableStateFlow<ForecastWeatherResponse?>(null)
    val forecastWeatherDataStateFlow = _forecastWeatherDataStateFlow.asStateFlow()


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    var auth = Firebase.auth;

    fun userLogOut() {
        auth.signOut()
    }

     fun getForecastWeatherData(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = weatherRepository.getForecastWeatherData(city)
            when (result) {
                is Result.Success<ForecastWeatherResponse> -> {
                    _forecastWeatherDataStateFlow.value = result.data
                }

                else -> {
                    onError((result as? Result.Error)?.message ?: "UnKnown Error")
                }
            }
            _isLoading.value = false
        }
    }

    private fun onError(s: String) {

    }

    fun getCurrentWeatherData(city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = weatherRepository.getCurrentWeatherData(city)
            when (result) {
                is Result.Success<CurrentWeatherResponse> -> {
                    _currentWeatherDataStateFlow.value = result.data
                }

                else -> {
                    onError((result as? Result.Error)?.message ?: "UnKnown Error")
                }
            }
            _isLoading.value = false
        }
    }


}