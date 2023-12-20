package com.example.finalproject

data class Order(val restaurant: Restaurant, val address: String, val items: ArrayList<FoodItem>, val total: Double, val date: String, val time: String)
