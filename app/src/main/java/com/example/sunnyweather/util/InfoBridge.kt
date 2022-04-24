package com.example.sunnyweather.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Person
import com.example.sunnyweather.logic.model.Place

object InfoBridge {

    var numberAddress = ""
    lateinit var intimatePerson: Person
    //联系人的手机号和名字
    var number = ""
    lateinit var numberPlace: Place

    private val numberLiveData = MutableLiveData<String>()
    private val addressLiveData = MutableLiveData<String>()

    val numberAddressLiveData = Transformations.switchMap(numberLiveData) {
        Repository.searchNumberPlace(it)
    }
    val numberPlaceLiveData = Transformations.switchMap(addressLiveData){
        Repository.searchPlaces(it)
    }

    fun setPerson(intimatePerson: Person) {
        this.intimatePerson = intimatePerson
    }
    fun searchNumberAddress(number: String) {
        numberLiveData.value = number
        this.number = number
    }

    fun searchNumberPlace(address: String) {
        addressLiveData.value = address
    }
}