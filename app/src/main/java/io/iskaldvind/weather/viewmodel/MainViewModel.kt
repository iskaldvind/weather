package io.iskaldvind.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.iskaldvind.weather.model.AppState
import io.iskaldvind.weather.model.Repository
import io.iskaldvind.weather.model.RepositoryImpl
import java.lang.Thread.sleep



class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {


    fun getLiveData() = liveDataToObserve


    fun getWeather() = getDataFromLocalSource()


    private fun getDataFromLocalSource() {
        Log.d("ORO", "ORO")
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
        }.start()
    }
}