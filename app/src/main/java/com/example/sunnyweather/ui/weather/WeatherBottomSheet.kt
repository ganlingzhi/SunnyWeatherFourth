package com.example.sunnyweather.ui.weather

import android.view.LayoutInflater
import com.example.sunnyweather.R
import com.google.android.material.bottomsheet.BottomSheetDialog

object WeatherBottomSheet {
    fun showBottomSheet(activity: WeatherActivity, contact: ContactInfo, callback: (() -> Unit)) {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.dialog_contact_weather, null)
        val bottomSheetDialog = BottomSheetDialog(activity, R.style.bottomSheetStyleWrapper)
    }

    data class ContactInfo(val name: String, val number: String, val place: String)
}