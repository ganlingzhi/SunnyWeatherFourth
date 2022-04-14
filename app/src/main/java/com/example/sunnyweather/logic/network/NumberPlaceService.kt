package com.example.sunnyweather.logic.network

import com.example.sunnyweather.logic.model.NumberPlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NumberPlaceService {
    @GET("/phonearea.php")

    fun searchIntimatePersonPlace(@Query("number") number: String): Call<NumberPlaceResponse>

}