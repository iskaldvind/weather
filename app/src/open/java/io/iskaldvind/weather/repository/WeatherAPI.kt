package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeather(
        @Suppress("SpellCheckingInspection") @Query("appid") key: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric"
    ): Call<WeatherDTO>
}