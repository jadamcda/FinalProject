package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class HomeTopFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  view = inflater.inflate(R.layout.fragment_home_top, container, false)
        val viewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        val rViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val recyclerH = view.findViewById<RecyclerView>(R.id.recyclerHorizontal)
        recyclerH.setHasFixedSize(true)
        val layout = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        recyclerH.layoutManager = layout

        val rList = viewModel.restaurantList

        val rAdapter = RAdapterH(rList)
        recyclerH.adapter = rAdapter

        rAdapter.onButtonClick = {
            println("clicked " + it.placeName)
            rViewModel.thisRestaurant = it
        }

        return view
    }
}