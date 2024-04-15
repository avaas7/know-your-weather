package com.av.knowYourWeather.di

import com.av.knowYourWeather.Dashboard.DashboardActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: DashboardActivity)
}