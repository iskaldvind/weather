package io.iskaldvind.weather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

const val ID = "id"
const val NAME = "name"
const val CURRENTTEMPERATURE = "currentTemperature"

@Entity
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val currentTemperature: Int = 0,
    val currentWeather: String = ""
)