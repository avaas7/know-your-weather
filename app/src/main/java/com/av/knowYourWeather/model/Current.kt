package com.av.knowYourWeather.model

import com.google.gson.annotations.SerializedName

data class Current(
    val feelslikeC: Any? = null,
    val uv: Any? = null,
    val lastUpdated: String? = null,
    @SerializedName("feelslike_f")
    val feelslikeF: Any? = null,
    val windDegree: Int? = null,
    val lastUpdatedEpoch: Int? = null,
    val isDay: Int? = null,
    val precipIn: Any? = null,
    val windDir: String? = null,
    @SerializedName("gust_mph")
    val gustMph: Any? = null,
    val tempC: Any? = null,
    val pressureIn: Any? = null,
    val gustKph: Any? = null,
    @SerializedName("temp_f")
    val tempF: Any? = null,
    @SerializedName("precip_mm")
    val precipMm: Any? = null,
    val cloud: Int? = null,
    val windKph: Any? = null,
    val condition: Condition? = null,
    @SerializedName("wind_mph")
    val windMph: Any? = null,
    @SerializedName("vis_km")
    val visKm: Any? = null,
    val humidity: Int? = null,
    @SerializedName("pressure_mb")
    val pressureMb: Any? = null,
    val visMiles: Any? = null
)