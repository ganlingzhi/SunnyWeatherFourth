package com.example.sunnyweather.ui.others.placesweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.ui.others.placesweather.fragment.FirstPlaceFragment
import com.example.sunnyweather.ui.others.placesweather.fragment.SecondPlaceFragment
import com.example.sunnyweather.ui.others.placesweather.fragment.ThirdPlaceFragment
import kotlinx.android.synthetic.main.activity_places_weather.*

class PlacesWeatherActivity : AppCompatActivity() {

    private lateinit var adapter: PlaceFragmentPagerAdapter
    private var placeList: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places_weather)
        initEvent()
    }

    fun initEvent() {
        for (i in 0..Repository.getPlaceList().size-1) {
            val weatherFragment = WeatherFragment()
            weatherFragment.setPlace(Repository.getPlaceList().get(i))
            placeList.add(weatherFragment)
        }

        adapter = PlaceFragmentPagerAdapter(supportFragmentManager)
        adapter.setFragmentList(placeList)

        placesViewPage.adapter = adapter
        placesViewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> top_rg_first_place.isChecked = true
                    1 -> top_rg_second_place.isChecked = true
                    2 -> top_rg_third_place.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        main_top_rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when (p1) {
                    top_rg_first_place.id -> placesViewPage.setCurrentItem(0)
                    top_rg_second_place.id -> placesViewPage.setCurrentItem(1)
                    else -> placesViewPage.setCurrentItem(2)
                }
            }

        })
        placesViewPage.setCurrentItem(0)
    }
}