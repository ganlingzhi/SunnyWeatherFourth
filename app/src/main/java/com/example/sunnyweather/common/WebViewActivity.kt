package com.example.sunnyweather.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import com.example.sunnyweather.BaseActivity
import com.example.sunnyweather.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity() {

    private var url = ""
    @SuppressLint("SetJavaScriptEnabled", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val setting = webView.settings
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        setting.useWideViewPort = true
        setting.javaScriptEnabled = true
        setting.loadWithOverviewMode = true
        setting.setGeolocationEnabled(true)
        setting.domStorageEnabled = true
        webView.requestFocus()
        webView.scrollBarStyle = 0
        setListener()
        getStringForIntent()

        webView.loadUrl(url)
    }
    private fun setListener() {
        webView_back.setOnClickListener {
            getAc().finish()
        }
    }
    private fun getStringForIntent() {
        url = intent.getStringExtra("url") ?: ""

    }
    companion object{
        fun openUrl(context: Context, url: String?) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }
}