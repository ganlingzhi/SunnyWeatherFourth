package com.example.sunnyweather.util

import android.annotation.SuppressLint
import android.content.Context
import com.example.sunnyweather.SunnyWeatherApplication


@SuppressLint("StaticFieldLeak")
object PrefUtils {

    val context = SunnyWeatherApplication.context
    private val sp = context.getSharedPreferences("SunnyWeatherSP", Context.MODE_PRIVATE)

    private val BOTTOM_SHEET_SHOW = "bottom_sheet_show"

    fun setBottomSheetShow() {
        sp.edit().putBoolean(BOTTOM_SHEET_SHOW, true).apply()
    }
    fun getBottomSheetShow(): Boolean {
        return sp.getBoolean(BOTTOM_SHEET_SHOW, false)
    }
}