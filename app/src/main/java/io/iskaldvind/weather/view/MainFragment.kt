package io.iskaldvind.weather.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.MainFragmentBinding
import io.iskaldvind.weather.model.AppState
import io.iskaldvind.weather.model.Cities
import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.Weathers
import io.iskaldvind.weather.viewmodel.MainViewModel



class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get(): MainFragmentBinding = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("HELLO", "HELLO")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeather()
    }


    private fun renderData(appState: AppState) {
        Log.d("PUR", "PUR")
        when (appState) {
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.mainLoadingLayout.visibility = View.GONE
                setData(weatherData)
            }
            is AppState.Loading -> {
                binding.mainLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeather() }
                    .show()
            }
        }
    }


    private fun setData(weatherData: Weather) {
        val city = when (weatherData.city) {
            Cities.MOSCOW -> requireActivity().getString(R.string.moscow)
            Cities.NEWYORK -> requireActivity().getString(R.string.new_york)
            Cities.TOKYO -> requireActivity().getString(R.string.tokyo)
            Cities.CAIRO -> requireActivity().getString(R.string.cairo)
        }
        binding.mainCity.text = city
        binding.mainTemperature.text = weatherData.currentTemperature.toString()
        val weather = when (weatherData.currentWeather) {
            Weathers.SUNNY -> requireActivity().getString(R.string.sunny)
            Weathers.CLOUDY -> requireActivity().getString(R.string.cloudy)
            Weathers.RAINY -> requireActivity().getString(R.string.rainy)
            Weathers.SNOWY -> requireActivity().getString(R.string.snowy)
            Weathers.THUNDER -> requireActivity().getString(R.string.thunder)
        }
        binding.mainWeather.text = weather
        binding.mainMorningTemperature.text = weatherData.morningTemperature.toString()
        binding.mainDayTemperature.text = weatherData.dayTemperature.toString()
        binding.mainEveningTemperature.text = weatherData.eveningTemperature.toString()
        binding.mainNightTemperature.text = weatherData.nightTemperature.toString()
        binding.mainCardTodayTemperatureMax.text = weatherData.todayTemperatureMax.toString()
        binding.mainCardTodayTemperatureMin.text = weatherData.todayTemperatureMin.toString()
        binding.mainCardTomorrowTemperatureMax.text = weatherData.tomorrowTemperatureMax.toString()
        binding.mainCardTomorrowTemperatureMin.text = weatherData.tomorrowTemperatureMin.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}