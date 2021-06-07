package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.WeatherDTO

interface DetailsRepository {
    fun getWeatherDetailsFromServer(lat: Double, lon: Double, callback: retrofit2.Callback<WeatherDTO>)
}