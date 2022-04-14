package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)

    data class Realtime(
        val temperature: Float,
        val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class AirQuality(val aqi: AQI, val description: DSP)
    data class AQI(val chn: Float)
    data class DSP(val chn: String)
    data class LifeIndex(val ultraviolet: Ultraviolet)
    data class Ultraviolet(val desc: String)
}