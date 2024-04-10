package com.av.digiLearn.model

data class ForecastdayItem(
    val date: String? = null,
    val astro: Astro? = null,
    val dateEpoch: Int? = null,
    val hour: List<HourItem?>? = null,
    val day: Day? = null
)