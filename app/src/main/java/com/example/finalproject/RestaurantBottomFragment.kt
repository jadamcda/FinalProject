package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RestaurantBottomFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaraunt_bottom, container, false)
        val rViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val menuRecycler = view.findViewById<RecyclerView>(R.id.menuRecycler)
        menuRecycler.setHasFixedSize(true)
        val layout = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        menuRecycler.layoutManager = layout

        val menuList = rViewModel.thisRestaurant.menu

        val menuAdapter = MenuAdapter(menuList)
        menuRecycler.adapter = menuAdapter


        return view
    }
}