package io.iskaldvind.weather.model

import io.iskaldvind.weather.model.Cities
import kotlin.math.roundToInt

const val MAX_TEMP = 30
const val MIN_TEMP = -30



data class Weather(
    val city: Cities = getRandomCity(),
    val currentTemperature: Int = getRandomTemperature(),
    val currentWeather: Weathers = getRandomWeather(),
    val morningTemperature: Int = getRandomTemperature(),
    val dayTemperature: Int = currentTemperature,
    val eveningTemperature: Int = getRandomTemperature(),
    val nightTemperature: Int = getRandomTemperature(),
    val todayTemperatureMax: Int = morningTemperature.coerceAtLeast(dayTemperature)
        .coerceAtLeast(eveningTemperature.coerceAtLeast(nightTemperature)),
    val todayTemperatureMin: Int = morningTemperature.coerceAtMost(dayTemperature)
        .coerceAtMost(eveningTemperature.coerceAtMost(nightTemperature)),
    val tomorrowTemperatureMax: Int = todayTemperatureMax + 5,
    val tomorrowTemperatureMin: Int = todayTemperatureMin - 5
)


fun getRandomCity(): Cities {
    return when ((Math.random() * 3).roundToInt()) {
        0 -> Cities.MOSCOW
        1 -> Cities.NEWYORK
        2 -> Cities.TOKYO
        else -> Cities.CAIRO
    }
}


fun getRandomWeather(): Weathers {
    return when ((Math.random() * 4).roundToInt()) {
        0 -> Weathers.SUNNY
        1 -> Weathers.CLOUDY
        2 -> Weathers.RAINY
        3 -> Weathers.SNOWY
        else -> Weathers.THUNDER
    }
}


fun getRandomTemperature(): Int {
    return (Math.random() * (MAX_TEMP - MIN_TEMP) - ((MAX_TEMP - MIN_TEMP)/2)).roundToInt()
}





