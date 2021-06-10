package io.iskaldvind.weather.model

data class WeatherDTO(
    val fact: FactDTO?
)

data class FactDTO(
    val temp: Int?,
    val condition: String?,
    val icon: String?
)
