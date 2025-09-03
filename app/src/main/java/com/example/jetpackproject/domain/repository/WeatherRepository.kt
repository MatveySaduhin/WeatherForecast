package com.example.jetpackproject.domain.repository

import com.example.jetpackproject.domain.util.Resource
import com.example.jetpackproject.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}