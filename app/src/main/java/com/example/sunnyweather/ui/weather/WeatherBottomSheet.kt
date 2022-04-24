package com.example.sunnyweather.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Place
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

object WeatherBottomSheet {
    fun showBottomSheet(
        activity: WeatherActivity,
        contactName:String,
        contactNumber: String,
        contactAddress: String,
        contactPlace: Place,
        callback: ((Place?) -> Unit)
    ) {
        val view =
            LayoutInflater.from(activity).inflate(R.layout.dialog_contact_weather, null)
        val bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(view)
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as ViewGroup)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        val TvContactName = view.findViewById<TextView>(R.id.contact_name)
        val TvContactNumber = view.findViewById<TextView>(R.id.contact_number)
        val TvContactAddress = view.findViewById<TextView>(R.id.contact_address)
        val ImgClose = view.findViewById<ImageView>(R.id.weather_bottom_item_close)
        val BtnClose = view.findViewById<Button>(R.id.button_close)
        val BtnLook = view.findViewById<Button>(R.id.have_a_look_weather_details)

        TvContactName.text = contactName
        TvContactNumber.text = contactNumber
        TvContactAddress.text = contactAddress

        ImgClose.setOnClickListener {
            bottomSheetDialog.dismiss()
            callback.invoke(null)
        }
        BtnClose.setOnClickListener {
            bottomSheetDialog.dismiss()
            callback.invoke(null)
        }
        BtnLook.setOnClickListener {
            bottomSheetDialog.dismiss()
            callback.invoke(contactPlace)
        }
        bottomSheetDialog.show()
    }
}