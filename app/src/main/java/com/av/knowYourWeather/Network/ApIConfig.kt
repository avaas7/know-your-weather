package com.av.knowYourWeather.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApIConfig {
    companion object {
        fun getApiService(): Apiservice {

            //Api response interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            //client
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            //Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(Apiservice::class.java)
        }
        const val API_KEY = "3c3cf85e45824f19bbe143607240304"
    }
}