package io.iskaldvind.weather.repository

import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.room.HistoryDao
import io.iskaldvind.weather.utils.convertHistoryEntityToWeather
import io.iskaldvind.weather.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

    override fun clear(): List<Weather> {
        localDataSource.clear()
        return getAllHistory()
    }
}