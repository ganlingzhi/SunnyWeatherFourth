package com.example.sunnyweather.ui.others.placesweather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class PlaceFragmentPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var listFragment: List<Fragment>
    private val fm: FragmentManager = fragmentManager


    override fun getItem(position: Int): Fragment {
        return listFragment.get(position)
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun setFragmentList(list: List<Fragment>) {
        listFragment = list
    }
}