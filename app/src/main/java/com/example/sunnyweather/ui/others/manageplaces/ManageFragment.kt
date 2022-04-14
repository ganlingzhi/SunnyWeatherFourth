package com.example.sunnyweather.ui.others.manageplaces

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.ui.place.PlaceAdapter
import kotlinx.android.synthetic.main.fragment_manage_places.*
import android.view.MenuItem
import android.widget.PopupMenu


class ManageFragment:Fragment() {

    lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.fragment_manage_places, container, false)
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        manage_searchPlaceEdit.setOnClickListener {
            val intent = Intent(activity, AddPlacesActivity::class.java)
            startActivity(intent)
        }
        val placeList = Repository.getPlaceList()

        adapter = PlaceAdapter(this, placeList)


        adapter.setOnItemClickListener(object : PlaceAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                val popupMenu = PopupMenu(context, view)
                popupMenu.inflate(R.menu.delete)
                popupMenu.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                    PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {
                        placeList.removeAt(position)
                        Repository.deletePlace(position)
                        adapter.notifyDataSetChanged()
                        return false
                    }
                })
                popupMenu.show()
            }
        })
        manage_places_recycleView.adapter = adapter
        manage_places_recycleView.layoutManager = LinearLayoutManager(activity)
        adapter.notifyDataSetChanged()

        if (placeList.isNotEmpty()) {
            manage_places_recycleView.visibility = View.VISIBLE
            managebgImageView.visibility = View.GONE
        } else {
            manage_places_recycleView.visibility = View.GONE
            managebgImageView.visibility = View.VISIBLE
        }
    }

}