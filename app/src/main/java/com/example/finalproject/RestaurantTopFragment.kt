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

class RestaurantTopFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaraunt_top, container, false)
        val rViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val imagesRecycler = view.findViewById<RecyclerView>(R.id.imagesRecycler)
        imagesRecycler.setHasFixedSize(true)
        val layout = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        imagesRecycler.layoutManager = layout

//        val imagesList : ArrayList<String> = rViewModel.thisRestaurant.images
//
//        val imagesAdapter = ImagesAdapter(imagesList)
//        imagesRecycler.adapter = imagesAdapter


        return view
    }
}