package io.iskaldvind.weather.view.support

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import io.iskaldvind.weather.model.WeatherDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val API_KEY = "4fe91c82-19da-4483-93a1-638f5cf32c9d"

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {

    fun loadWeather() {
        try {
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
            val handler = Handler(Looper.getMainLooper())
            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = url.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        API_KEY
                    )
                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val json = getLines(bufferedReader)

                    val weatherDTO: WeatherDTO = Gson().fromJson(json, WeatherDTO::class.java)

                    handler.post {
                        listener.onLoaded(weatherDTO)
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    handler.post {
                        listener.onFailed(e)
                    }
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }


    private fun getLines (reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


    interface WeatherLoaderListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}