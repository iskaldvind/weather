package io.iskaldvind.weather.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import io.iskaldvind.weather.R
import io.iskaldvind.weather.app.AppState
import io.iskaldvind.weather.databinding.FragmentDetailsBinding
import io.iskaldvind.weather.model.*
import io.iskaldvind.weather.utils.showSnackBar
import io.iskaldvind.weather.viewmodel.DetailsViewModel


class DetailsFragment(val passedWeather: Weather) : Fragment() {

    companion object {
        fun newInstance(weather: Weather): DetailsFragment = DetailsFragment(weather)
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get(): FragmentDetailsBinding = _binding!!
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromRemoteSource(passedWeather.city.lat, passedWeather.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.details.visibility = View.VISIBLE
                binding.detailsLoadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.details.visibility = View.GONE
                binding.detailsLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.details.visibility = View.VISIBLE
                binding.detailsLoadingLayout.visibility = View.GONE
                binding.details.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getWeatherFromRemoteSource(
                            passedWeather.city.lat,
                            passedWeather.city.lon
                        )
                    })
            }
        }
    }

    private fun setWeather(weather: Weather) {
        val city = passedWeather.city
        saveCity(city, weather)
        binding.detailsCity.text = city.name
        weather.icon?.let {
            GlideToVectorYou.justLoadImage(
                activity,
                Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
                binding.detailsWeatherIcon
            )
            binding.detailsTemperature.text = weather.currentTemperature.toString()
            binding.detailsWeather.text = weather.currentWeather
        }

        Picasso
            .get()
            .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
            .into(binding.detailsHeaderIcon)
    }


    private fun saveCity(
        city: City,
        weather: Weather
    ) {
        viewModel.saveCityToDB(
            Weather(
                city,
                weather.currentTemperature,
                weather.currentWeather
            )
        )
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}