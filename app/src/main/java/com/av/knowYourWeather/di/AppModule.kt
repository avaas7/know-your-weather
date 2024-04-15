package com.av.knowYourWeather.di

import com.av.knowYourWeather.Repository.WeatherRepository
import com.av.knowYourWeather.Viewmodel.WeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AppModule {
     @Provides
     fun provideWeatherRepository(): WeatherRepository {
         return WeatherRepository()
     }

    @Provides
    fun provideWeatherViewModel(weatherRepository: WeatherRepository): WeatherViewModel {
        return WeatherViewModel(weatherRepository)
    }
}