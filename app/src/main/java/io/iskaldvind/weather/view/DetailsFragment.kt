package io.iskaldvind.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.FragmentDetailsBinding
import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.Weathers

class DetailsFragment(val weather: Weather) : Fragment() {

    companion object {
        fun newInstance(weather: Weather): DetailsFragment = DetailsFragment(weather)
    }


    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get(): FragmentDetailsBinding = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = weather.city
        binding.detailsCity.text = city
        binding.detailsTemperature.text = weather.currentTemperature.toString()
        val weatherText = when (weather.currentWeather) {
            Weathers.SUNNY -> requireActivity().getString(R.string.sunny)
            Weathers.CLOUDY -> requireActivity().getString(R.string.cloudy)
            Weathers.RAINY -> requireActivity().getString(R.string.rainy)
            Weathers.SNOWY -> requireActivity().getString(R.string.snowy)
            Weathers.THUNDER -> requireActivity().getString(R.string.thunder)
        }
        binding.detailsWeather.text = weatherText
        binding.detailsMorningTemperature.text = weather.morningTemperature.toString()
        binding.detailsDayTemperature.text = weather.dayTemperature.toString()
        binding.detailsEveningTemperature.text = weather.eveningTemperature.toString()
        binding.detailsNightTemperature.text = weather.nightTemperature.toString()
        binding.detailsCardTodayTemperatureMax.text = weather.todayTemperatureMax.toString()
        binding.detailsCardTodayTemperatureMin.text = weather.todayTemperatureMin.toString()
        binding.detailsCardTomorrowTemperatureMax.text = weather.tomorrowTemperatureMax.toString()
        binding.detailsCardTomorrowTemperatureMin.text = weather.tomorrowTemperatureMin.toString()
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}