package com.example.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.ui.theme.WeatherForecastTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    var temperature by remember { mutableFloatStateOf(0f) }
    var city by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }

    val geocodingApi = remember {
        Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApi::class.java)
    }
    val weatherApi = remember {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("City name") }
        )

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Step 1: Convert city name to coordinates
                    val geocodingResponse = geocodingApi.getCoordinates(input)
                    if (geocodingResponse.isNotEmpty()) {
                        val lat = geocodingResponse[0].lat.toFloat()
                        val lon = geocodingResponse[0].lon.toFloat()

                        // Step 2: Fetch weather using coordinates
                        val weatherResponse = weatherApi.getWeather(lat, lon)
                        temperature = weatherResponse.current_weather.temperature
                        city = input
                    }
                } catch (e: Exception) {
                    city = "Error: ${e.message}"
                }
            }
        }) {
            Text("Get Weather")
        }
        Text("${temperature}°C", fontSize = 32.sp)
        Text(city, fontSize = 24.sp)
    }
}