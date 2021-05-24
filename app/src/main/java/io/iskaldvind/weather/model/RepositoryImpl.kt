package io.iskaldvind.weather.model

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