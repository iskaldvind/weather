package io.iskaldvind.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.iskaldvind.weather.R
import io.iskaldvind.weather.model.WeatherList


class MainActivity : AppCompatActivity() {

    private val defaultWeatherList = WeatherList.RUS
    var lastWeatherList = defaultWeatherList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}