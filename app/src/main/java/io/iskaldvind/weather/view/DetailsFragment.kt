package io.iskaldvind.weather.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.FragmentDetailsBinding
import io.iskaldvind.weather.experimental.DetailsService
import io.iskaldvind.weather.experimental.LATITUDE_EXTRA
import io.iskaldvind.weather.experimental.LONGITUDE_EXTRA
import io.iskaldvind.weather.model.FactDTO
import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.WeatherDTO
import io.iskaldvind.weather.model.Weathers
import io.iskaldvind.weather.view.support.WeatherLoader

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_TEMP_EXTRA = "TEMPERATURE"
const val DETAILS_CONDITION_EXTRA = "CONDITION"
private const val TEMP_INVALID = -100
private const val PROCESS_ERROR = "Обработка ошибки"


class DetailsFragment(val weather: Weather) : Fragment() {

    companion object {
        fun newInstance(weather: Weather): DetailsFragment = DetailsFragment(weather)
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get(): FragmentDetailsBinding = _binding!!

    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {

            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                Log.d("", throwable.toString())
            }
        }

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> showError(DETAILS_INTENT_EMPTY_EXTRA)
                DETAILS_DATA_EMPTY_EXTRA -> showError(DETAILS_DATA_EMPTY_EXTRA)
                DETAILS_RESPONSE_EMPTY_EXTRA -> showError(DETAILS_RESPONSE_EMPTY_EXTRA)
                DETAILS_REQUEST_ERROR_EXTRA -> showError(DETAILS_REQUEST_ERROR_EXTRA)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> showError(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                DETAILS_URL_MALFORMED_EXTRA -> showError(DETAILS_URL_MALFORMED_EXTRA)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> displayWeather(
                    WeatherDTO(
                        FactDTO(
                            intent.getIntExtra(DETAILS_TEMP_EXTRA, TEMP_INVALID),
                            intent.getStringExtra(DETAILS_CONDITION_EXTRA)
                        )
                    )
                )
                else -> showError(PROCESS_ERROR)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsLoadingLayout.visibility = View.VISIBLE
        binding.details.visibility = View.GONE
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
        //val loader = WeatherLoader(onLoadListener, weather.city.lat, weather.city.lon)
        //loader.loadWeather()
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(
                    LATITUDE_EXTRA,
                    weather.city.lat
                )
                putExtra(
                    LONGITUDE_EXTRA,
                    weather.city.lon
                )
            })
        }

    }


    private fun displayWeather(weatherDTO: WeatherDTO) {
        with (binding) {
            detailsLoadingLayout.visibility = View.GONE
            details.visibility = View.VISIBLE
            val city = weather.city.name
            detailsWeather.text = weatherDTO.fact?.condition
            detailsTemperature.text = weatherDTO.fact?.temp.toString()
        }
    }

    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
    }

    private fun showError(error: String?) {
        error?.let{
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
}