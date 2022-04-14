package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceResponse(val status: String, val query: String, val places: List<Place>)
data class Place(
    var name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)