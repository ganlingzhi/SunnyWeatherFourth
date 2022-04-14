package com.example.sunnyweather.ui.others.slideslipmenu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import com.example.sunnyweather.R
import kotlinx.android.synthetic.main.activity_slide_slip_main.*
import kotlinx.android.synthetic.main.activity_weather.*

class SlideSlipMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_slip_main)
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("")
        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24)
        toolbar.setNavigationOnClickListener{
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }
}


