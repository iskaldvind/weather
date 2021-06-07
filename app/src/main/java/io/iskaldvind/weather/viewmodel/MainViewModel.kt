package io.iskaldvind.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.iskaldvind.weather.model.AppState
import io.iskaldvind.weather.repository.Repository
import io.iskaldvind.weather.repository.RepositoryImpl
import java.lang.Thread.sleep


class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRus = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRus = false)

    private fun getDataFromLocalSource(isRus: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            val data = when (isRus) {
                true -> repository.getWeatherFromLocalStorageRus()
                false -> repository.getWeatherFromLocalStorageWorld()
            }
            liveDataToObserve.postValue(AppState.Success(data))
        }.start()
    }
}