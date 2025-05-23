package com.example.weatherforecast

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast?current_weather=true")
    suspend fun getWeather(
        @Query("latitude") lat: Float,
        @Query("longitude") lon: Float
    ): WeatherResponse
}

data class WeatherResponse(val current_weather: CurrentWeather)
data class CurrentWeather(val temperature: Float)