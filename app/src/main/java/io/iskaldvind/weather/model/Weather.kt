package io.iskaldvind.weather.model

import kotlin.math.roundToInt

const val MAX_TEMP = 30
const val MIN_TEMP = -30

val CITIES_RUS = listOf("Moscow", "St. Petersburg", "Samara")
val CITIES_WORLD = listOf("New York", "Berlin", "Tokyo")

data class Weather(
    val city: String = getRandomCity(),
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


fun getWorldCities(): List<Weather> {
    val worldWeather = mutableListOf<Weather>()
    CITIES_WORLD.forEach {
        worldWeather.add(Weather(city = it))
    }
    return worldWeather.toList()
}


fun getRusCities(): List<Weather> {
    val rusWeather = mutableListOf<Weather>()
    CITIES_RUS.forEach {
        rusWeather.add(Weather(city = it))
    }
    return rusWeather.toList()
}


private fun getRandomWeather(): Weathers {
    return when ((Math.random() * 4).roundToInt()) {
        0 -> Weathers.SUNNY
        1 -> Weathers.CLOUDY
        2 -> Weathers.RAINY
        3 -> Weathers.SNOWY
        else -> Weathers.THUNDER
    }
}


private fun getRandomTemperature(): Int {
    return (Math.random() * (MAX_TEMP - MIN_TEMP) - ((MAX_TEMP - MIN_TEMP)/2)).roundToInt()
}


private fun getRandomCity(): String {
    val rnd = (Math.random() * (CITIES_RUS.size - 1)).roundToInt()
    return CITIES_RUS[rnd]
}


