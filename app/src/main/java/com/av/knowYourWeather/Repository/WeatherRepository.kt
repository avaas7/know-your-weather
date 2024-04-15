package com.av.knowYourWeather.Repository

import com.av.knowYourWeather.Network.ApIConfig
import com.av.knowYourWeather.model.CurrentWeatherResponse
import com.av.knowYourWeather.model.ForecastWeatherResponse
import com.av.knowYourWeather.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class WeatherRepository @Inject constructor() {

    suspend fun getForecastWeatherData(city: String): Result<ForecastWeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val client = ApIConfig.getApiService().getForecastWeather(city = city, days = 14)
                val response = client.execute()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody!=null) {
                        Result.Success(responseBody)
                    } else {
                        Result.Error("Data Processing Error")
                    }
                }
                else
                {
                    Result.Error("Network Error: ${response.code()}")
                }
            }
            catch (e: Exception)
            {
                Result.Error("Error ${e.message}")
            }
        }
    }

    suspend fun getCurrentWeatherData(city: String): Result<CurrentWeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val client = ApIConfig.getApiService().getCurrentWeather(city = city)
                val response = client.execute()

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody!=null) {
                        Result.Success(responseBody)
                    } else {
                        Result.Error("Data Processing Error")
                    }
                }
                else
                {
                    Result.Error("Network Error: ${response.code()}")
                }
            }
            catch (e: Exception)
            {
                Result.Error("Error ${e.message}")
            }
        }
    }

}