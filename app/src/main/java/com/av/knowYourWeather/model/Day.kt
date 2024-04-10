package com.av.knowYourWeather.model

import com.google.gson.annotations.SerializedName

data class Day(
    val avgvisKm: Any? = null,
    val uv: Any? = null,
    @SerializedName("avgtemp_f")
    val avgtempF: Any? = null,
    val avgtempC: Any? = null,
    val dailyChanceOfSnow: Int? = null,
    val maxtempC: Any? = null,
    @SerializedName("maxtemp_f")
    val maxtempF: Any? = null,
    val mintempC: Any? = null,
    val avgvisMiles: Any? = null,
    val dailyWillItRain: Int? = null,
    val mintempF: Any? = null,
    val totalprecipIn: Any? = null,
    val totalsnowCm: Any? = null,
    val avghumidity: Int? = null,
    val condition: Condition? = null,
    val maxwindKph: Any? = null,
    @SerializedName("maxwind_mph")
    val maxwindMph: Any? = null,
    val dailyChanceOfRain: Int? = null,
    val totalprecipMm: Any? = null,
    val dailyWillItSnow: Int? = null
)
