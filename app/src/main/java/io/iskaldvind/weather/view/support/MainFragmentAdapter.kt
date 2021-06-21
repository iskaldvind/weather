package io.iskaldvind.weather.view.support

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.iskaldvind.weather.R
import io.iskaldvind.weather.model.Weather

class MainFragmentAdapter(
    private var onItemViewClickListener: ((Weather) -> Unit)?
    ) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf()


    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }


    fun removeListener() {
        onItemViewClickListener = null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_recycler_item, parent, false) as View)
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }


    override fun getItemCount(): Int {
        return weatherData.size
    }


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.main_recycler_item_text).text = weather.city.name
                setOnClickListener {
                    onItemViewClickListener?.invoke(weather)
                }
            }
        }
    }
}