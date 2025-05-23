package com.example.weatherforecast

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("search?format=json&limit=1")
    suspend fun getCoordinates(
        @Query("q") cityName: String
    ): List<GeocodingResponse>
}

data class GeocodingResponse(
    val lat: String,  // Latitude
    val lon: String   // Longitude
)