package com.example.finalproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RestaurantViewModel : ViewModel() {
    var thisRestaurant : Restaurant = Restaurant("default", "default", ArrayList<Int>(), ArrayList<FoodItem>())

}