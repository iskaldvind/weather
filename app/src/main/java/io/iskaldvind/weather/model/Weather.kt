package io.iskaldvind.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val currentTemperature: Int = 0,
    val currentWeather: String = "sunny",
    val morningTemperature: Int = 0,
    val dayTemperature: Int = 0,
    val eveningTemperature: Int = 0,
    val nightTemperature: Int = 0,
    val todayTemperatureMax: Int = morningTemperature.coerceAtLeast(dayTemperature)
        .coerceAtLeast(eveningTemperature.coerceAtLeast(nightTemperature)),
    val todayTemperatureMin: Int = morningTemperature.coerceAtMost(dayTemperature)
        .coerceAtMost(eveningTemperature.coerceAtMost(nightTemperature)),
    val tomorrowTemperatureMax: Int = 0,
    val tomorrowTemperatureMin: Int = 0,
    val icon: String? = "bkn_n"
) : Parcelable


fun getDefaultCity() = City("Moscow", 55.45, 37.37)


fun getWorldCities(): List<Weather> {
    val worldWeather = mutableListOf<Weather>()
    worldWeather.add(Weather(city = City("New York", 40.42, 74.00)))
    worldWeather.add(Weather(city = City("Berlin", 52.31, 13.24)))
    return worldWeather.toList()
}


fun getRusCities(): List<Weather> {
    val rusWeather = mutableListOf<Weather>()
    rusWeather.add(Weather(city = City("Moscow", 55.45, 37.37)))
    rusWeather.add(Weather(city = City("St. Petersburg", 59.57, 30.19)))
    return rusWeather.toList()
}
