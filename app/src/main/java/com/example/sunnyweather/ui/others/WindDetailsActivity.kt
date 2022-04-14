package com.example.sunnyweather.ui.others

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.example.sunnyweather.R
import kotlinx.android.synthetic.main.activity_wind_dtails.*

class WindDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wind_dtails)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        val mWebSetting = windDetailsWebView.settings
        windDetailsWebView.loadUrl("https://docs.seniverse.com/product/data/wind.html")

        windDetailsWebView.webViewClient = WebViewClient()


    }
}