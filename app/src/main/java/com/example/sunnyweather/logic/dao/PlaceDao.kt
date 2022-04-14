package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.Place
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object PlaceDao {

    fun deletePlace(pos: Int) {
        val placeList: ArrayList<Place> = getPlaceList()
        placeList.removeAt(pos)
        val json = Gson().toJson(placeList)
        sharedPreferences().edit() {
            putString("placeList", json)
            commit()
        }
    }

    fun addPlaceList(place: Place) {
        val placeList: ArrayList<Place> = getPlaceList()
        for (placeDao in placeList) {
            if (place.name == placeDao.name) {
                return
            }
        }
        placeList.add(place)
        val json = Gson().toJson(placeList)
        sharedPreferences().edit() {
            putString("placeList", json)
            commit()
        }
    }

    fun getPlaceList(): ArrayList<Place> {
        val placeListJson = sharedPreferences().getString("placeList", null)
        if (placeListJson == null)return ArrayList<Place>()

        val type = object : TypeToken<ArrayList<Place>>() {}.type
        val gson = Gson()
        val placeList: ArrayList<Place> = gson.fromJson(placeListJson, type)

        return placeList
    }

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences(
        "sunny_weather",
        Context.MODE_PRIVATE
    )

}