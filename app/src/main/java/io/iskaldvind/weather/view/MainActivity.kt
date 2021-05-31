package io.iskaldvind.weather.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import io.iskaldvind.weather.R
import io.iskaldvind.weather.databinding.MainActivityBinding
import io.iskaldvind.weather.databinding.MainActivityWebwievBinding
import io.iskaldvind.weather.model.WeatherList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {

    private val defaultWeatherList = WeatherList.RUS
    var lastWeatherList = defaultWeatherList

//    private lateinit var binding: MainActivityWebwievBinding
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = MainActivityWebwievBinding.inflate(layoutInflater)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.ok.setOnClickListener(clickListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }


//    var clickListener: View.OnClickListener = object : View.OnClickListener {
//
//        @RequiresApi(Build.VERSION_CODES.N)
//        override fun onClick(v: View?) {
//
//            try {
//                val url = URL(binding.url.text.toString())
//
//                val handler = Handler(Looper.getMainLooper())
//                Thread {
//                    var urlConnection: HttpsURLConnection? = null
//
//                    try {
//                        urlConnection = url.openConnection() as HttpsURLConnection
//                        urlConnection.requestMethod = "GET"
//                        urlConnection.readTimeout = 10000
//                        val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
//                        val result = getLines(reader)
//
//                        handler.post {
//                            binding.webview.loadData(result, "text/html; charset=utf-8", "utf-8")
//                        }
//                    } catch (e: Exception) {
//                        Log.e("", "Fail connection", e)
//                        e.printStackTrace()
//                    } finally {
//                        urlConnection?.disconnect()
//                    }
//                }.start()
//            } catch (e: MalformedURLException) {
//                Log.e("", "Fail URI", e)
//                e.printStackTrace()
//            }
//        }
//
//        @RequiresApi(Build.VERSION_CODES.N)
//        private fun getLines (reader: BufferedReader): String {
//            return reader.lines().collect(Collectors.joining("\n"))
//        }
//    }
}