package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.Weather

interface Repository {

    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorageRus(): List<Weather>

    fun getWeatherFromLocalStorageWorld(): List<Weather>

}