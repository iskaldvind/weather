package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun clear(): List<Weather>
    fun saveEntity(weather: Weather)
}
