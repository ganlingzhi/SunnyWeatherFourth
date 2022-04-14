package com.example.sunnyweather.logic.model

data class NumberPlaceResponse(val code:Int,val data:Data) {
    data class Data(val province: String, val city: String, val sp: String)
}