package io.iskaldvind.weather.utils

import io.iskaldvind.weather.model.FactDTO
import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.WeatherDTO
import io.iskaldvind.weather.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(city = getDefaultCity(), currentTemperature = fact.temp!!, currentWeather = fact.condition!!, icon = fact.icon))
}