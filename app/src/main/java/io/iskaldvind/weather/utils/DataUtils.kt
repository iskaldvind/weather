package io.iskaldvind.weather.utils

import io.iskaldvind.weather.model.*
import io.iskaldvind.weather.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(city = getDefaultCity(), currentTemperature = fact.temp!!, currentWeather = fact.condition!!, icon = fact.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.name, 0.0, 0.0), it.currentTemperature, it.currentWeather)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.currentTemperature, weather.currentWeather)
}