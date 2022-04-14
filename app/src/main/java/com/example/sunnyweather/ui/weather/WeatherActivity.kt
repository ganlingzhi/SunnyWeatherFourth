package com.example.sunnyweather.ui.weather

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.BaseActivity
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.nc.JobBottomSheet
import com.example.sunnyweather.ui.others.manageplaces.ManagePlacesActivity
import com.example.sunnyweather.ui.others.WindDetailsActivity
import com.example.sunnyweather.ui.others.placesweather.PlacesWeatherActivity
import com.example.sunnyweather.util.WindUtil
import com.example.sunnyweather.util.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import kotlinx.android.synthetic.main.wind_forecast.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseActivity() {

    val viewModel:WeatherViewModel by lazy {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getAc().application)
        ViewModelProvider(getAc(), factory).get(WeatherViewModel::class.java)
    }
    private var lastBackPressTime = -1L

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_weather)

        getIntentString()

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        setting.setOnClickListener {
            popMenu()
        }
        moreWindyInfo.setOnClickListener {
            val intent = Intent(this, WindDetailsActivity::class.java)
            startActivity(intent)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    /**
     * WeatherActivity启动模式为SingleTask
     * SingleTask的intent数据刷新
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        getIntentString()
        refreshWeather()
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (lastBackPressTime == -1L || currentTime - lastBackPressTime >= 2000) {
            showBackPressTip()
            lastBackPressTime = currentTime
        } else {
            finish()
        }
    }

    private fun showBottomSheet() {

    }



    private fun popMenu() {
        val popupMenu = PopupMenu(this, setting)
        popupMenu.inflate(R.menu.setting_options)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener{ item: MenuItem ->
            when (item.itemId) {
                R.id.manage_place -> {
                    val intent = Intent(this, ManagePlacesActivity::class.java)
                    startActivity(
                        intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                    )

                }
                R.id.read_contacts -> {
                    val intent = Intent(this, PlacesWeatherActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }

    private fun showBackPressTip() {
        Toast.makeText(this, "再按一次退出彩云", Toast.LENGTH_SHORT).show()
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }

    private fun getIntentString() {
        viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        viewModel.placeName = intent.getStringExtra("place_name") ?: ""
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        //填充now.xml布局中的数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = if (realtime.airQuality.description.chn != "缺数据") {
            "空气指数 ${realtime.airQuality.aqi.chn.toInt()} ${realtime.airQuality.description.chn}"
        } else {
            "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        }
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        windyForecastLayout.removeAllViews()
        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText

            forecastLayout.addView(view)
        }

        for (i in 0 until days) {
            val skycon = daily.skycon[i]

            val maxWind = daily.wind[i]
            val windView = LayoutInflater.from(this)
                .inflate(R.layout.wind_forecast_item, windyForecastLayout, false)
            val windDate = windView.findViewById(R.id.windDate) as TextView
            val windDirection = windView.findViewById(R.id.windDirection) as TextView
            val windLevel = windView.findViewById(R.id.windLevel) as TextView
            val windDescription = windView.findViewById(R.id.windDescription) as TextView
            val wind = WindUtil.getWind(maxWind.max.speed, maxWind.max.direction)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            windDate.text = simpleDateFormat.format(skycon.date)
            windDirection.text = wind.direction
            windLevel.text = wind.level
            windDescription.text = wind.description
            windyForecastLayout.addView(windView)

        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = realtime.lifeIndex.ultraviolet.desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}