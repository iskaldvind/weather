package io.iskaldvind.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.MainFragmentBinding
import io.iskaldvind.weather.model.AppState
import io.iskaldvind.weather.model.Weather
import io.iskaldvind.weather.model.WeatherList
import io.iskaldvind.weather.view.support.MainFragmentAdapter
import io.iskaldvind.weather.viewmodel.MainViewModel



class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get(): MainFragmentBinding = _binding!!
    private var isDataSetRus: Boolean = true


    @Suppress("IfThenToSafeAccess")
    private val adapter = MainFragmentAdapter(
        object : OnItemViewClickListener {
            override fun onItemViewClick(weather: Weather) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(weather))
                    .addToBackStack("")
                    .commit()
            }
        }
    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecycler.adapter = adapter
        binding.mainFAB.setOnClickListener {
            changeWeatherDataSet()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        when ((requireActivity() as MainActivity).lastWeatherList) {
            WeatherList.RUS -> viewModel.getWeatherFromLocalSourceRus()
            WeatherList.WORLD -> viewModel.getWeatherFromLocalSourceWorld()
        }
    }


    private fun changeWeatherDataSet() {
        val activity = requireActivity() as MainActivity
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFAB.setImageResource(R.drawable.earth)
            activity.lastWeatherList = WeatherList.WORLD
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFAB.setImageResource(R.drawable.rus)
            activity.lastWeatherList = WeatherList.RUS
        }
        isDataSetRus = !isDataSetRus
    }


    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainLoadingLayout.visibility = View.GONE
                Snackbar.make(requireView(), "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}