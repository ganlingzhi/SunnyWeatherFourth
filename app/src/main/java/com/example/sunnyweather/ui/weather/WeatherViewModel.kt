package com.example.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Location
import com.example.sunnyweather.logic.model.Person
import com.example.sunnyweather.logic.model.Place

class WeatherViewModel : ViewModel() {

    var numberAddress = ""

    lateinit var numberPlace: Place


    private val numberLiveData = MutableLiveData<String>()
    private val addressLiveData = MutableLiveData<String>()

    val numberAddressLiveData = Transformations.switchMap(numberLiveData) {
        Repository.searchNumberPlace(it)
    }

    val numberPlaceLiveData = Transformations.switchMap(addressLiveData){
        Repository.searchPlaces(it)
    }

    private val locationLiveData = MutableLiveData<Location>()




    var locationLng = ""
    var locationLat = ""
    var placeName = ""
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

    fun searchNumberPlace(address: String) {
        addressLiveData.value = address
    }

    fun searchNumberAddress(number: String) {
        numberLiveData.value = number
    }
}