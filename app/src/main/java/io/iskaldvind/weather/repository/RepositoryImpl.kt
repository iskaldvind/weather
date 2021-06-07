package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.getRusCities
import io.iskaldvind.weather.model.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRusCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }
}