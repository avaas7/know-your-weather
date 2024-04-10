package com.av.digiLearn.Network

import com.av.digiLearn.model.CurrentWeatherResponse
import com.av.digiLearn.model.ForecastWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Apiservice {

    @GET("current.json")
    fun getCurrentWeather(
    @Query("key") key:String=ApIConfig.API_KEY,
    @Query("q") city:String,
    @Query("aqi") aqi:String="no"
    ): Call<CurrentWeatherResponse>

    @GET("forecast.json")
    fun getForecastWeather(
        @Query("key") key:String=ApIConfig.API_KEY,
        @Query("q") city:String,
        @Query("days") days:Int,
        @Query("aqi") aqi:String="no"
    ): Call<ForecastWeatherResponse>
}