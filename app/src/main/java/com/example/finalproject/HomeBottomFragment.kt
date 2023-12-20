package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class HomeBottomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_bottom, container, false)
        val fViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        val rViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val recyclerV = view.findViewById<RecyclerView>(R.id.recyclerVertical)
        recyclerV.setHasFixedSize(true)
        val layout = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerV.layoutManager = layout

        val rList = fViewModel.restaurantList

        val rAdapter = RAdapterV(rList)
        recyclerV.adapter = rAdapter


        rAdapter.onButtonClick = {
            println("clicked " + it.placeName)
            rViewModel.thisRestaurant = it
        }

        return view
    }
}