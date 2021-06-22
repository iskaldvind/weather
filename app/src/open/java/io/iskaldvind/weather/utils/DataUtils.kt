package io.iskaldvind.weather.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.iskaldvind.weather.model.*
import io.iskaldvind.weather.room.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val weather: WeatherO = weatherDTO.weather!![0]
    val main: MainO = weatherDTO.main!!
    return listOf(Weather(city = getDefaultCity(), currentTemperature = main.temp!!.toInt(), currentWeather = weather.description!!, icon = weather.icon))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.name, 0.0, 0.0), it.currentTemperature, it.currentWeather)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.currentTemperature, weather.currentWeather)
}

fun getIcon(id: String, activity: Activity, widget: ImageView) {
    val url = "http://openweathermap.org/img/wn/${id}@2x.png"
    Picasso
        .get()
        .load(url)
        .into(widget)
}