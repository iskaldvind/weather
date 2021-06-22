package io.iskaldvind.weather.utils

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
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

fun getIcon(id: String, activity: Activity, widget: ImageView) {
    val url = "https://yastatic.net/weather/i/icons/blueye/color/svg/${id}.svg"
    GlideToVectorYou.justLoadImage(
        activity,
        Uri.parse(url),
        widget
    )
}