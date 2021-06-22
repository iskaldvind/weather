package io.iskaldvind.weather.model

data class WeatherDTO(
    val weather: List<WeatherO>?,
    val main: MainO?
)

data class WeatherO(
    val description: String?,
    val icon: String?
)

data class MainO(
    val temp: Float?
)