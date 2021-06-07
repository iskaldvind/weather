package io.iskaldvind.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.MainActivityBinding
import io.iskaldvind.weather.model.WeatherList


class MainActivity : AppCompatActivity() {

    private val defaultWeatherList = WeatherList.RUS
    var lastWeatherList = defaultWeatherList

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}