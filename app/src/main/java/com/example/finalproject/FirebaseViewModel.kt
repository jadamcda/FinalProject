package com.example.finalproject

import android.media.Image
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseViewModel : ViewModel() {
    var auth: FirebaseAuth = Firebase.auth

    var userName = ""

    var imagesList : ArrayList<Int> = ArrayList()
    var foodList : ArrayList<FoodItem> = ArrayList()

    var menu1 : ArrayList<FoodItem> = ArrayList()
    var menu2 : ArrayList<FoodItem> = ArrayList()
    var menu3 : ArrayList<FoodItem> = ArrayList()
    var menu4 : ArrayList<FoodItem> = ArrayList()
    var menu5 : ArrayList<FoodItem> = ArrayList()
    var menu6 : ArrayList<FoodItem> = ArrayList()
    var menu7 : ArrayList<FoodItem> = ArrayList()
    var menu8 : ArrayList<FoodItem> = ArrayList()
    var menu9 : ArrayList<FoodItem> = ArrayList()
    var menu10 : ArrayList<FoodItem> = ArrayList()

    var restaurant1 : Restaurant
    var restaurant2 : Restaurant
    var restaurant3 : Restaurant
    var restaurant4 : Restaurant
    var restaurant5 : Restaurant
    var restaurant6 : Restaurant
    var restaurant7 : Restaurant
    var restaurant8 : Restaurant
    var restaurant9 : Restaurant
    var restaurant10 : Restaurant

    var restaurantList : ArrayList<Restaurant>
    var favoritesList : ArrayList<Restaurant>

    init {
        imagesList.add(R.drawable.burger)
        imagesList.add(R.drawable.fries)
        imagesList.add(R.drawable.yeeclaw)

        foodList = arrayListOf(FoodItem("food1", 4.00, 0), FoodItem("food2", 5.00, 0), FoodItem("food3", 1.00, 0))

        menu1 = arrayListOf(FoodItem("chicken nuggets", 5.59, 0), FoodItem("fries", 3.39, 0), FoodItem("McFlurry", 4.99, 0))
        menu2 = arrayListOf(FoodItem("chicken nuggets", 1.79, 0), FoodItem("fries", 1.99, 0), FoodItem("Frosty", 1.99, 0))
        menu3 = arrayListOf(FoodItem("soft taco", 1.99, 0), FoodItem("Burrito Supreme", 5.89, 0), FoodItem("nachos", 2.39, 0))
        menu4 = arrayListOf(FoodItem("cheeseburger", 10.39, 0), FoodItem("fries", 6.19, 0), FoodItem("milkshake", 5.79, 0))
        menu5 = arrayListOf(FoodItem("cheese pizza", 11.60, 0), FoodItem("sub sandwich", 9.95, 0), FoodItem("garlic bread", 4.95, 0))
        menu6 = arrayListOf(FoodItem("cheese pizza", 9.90, 0), FoodItem("breadstix", 4.75, 0), FoodItem("ice cream", 6.99, 0))
        menu7 = arrayListOf(FoodItem("dumplings", 7.95, 0), FoodItem("curry", 8.95, 0), FoodItem("tea", 2.50, 0))
        menu8 = arrayListOf(FoodItem("burrito", 10.70, 0), FoodItem("burrito bowl", 10.70, 0), FoodItem("tacos", 10.70, 0))
        menu9 = arrayListOf(FoodItem("gyro", 8.49, 0), FoodItem("falafel", 7.50, 0), FoodItem("baklava", 4.49, 0))
        menu10 = arrayListOf(FoodItem("butter chicken", 15.95, 0), FoodItem("chicken curry", 15.95, 0), FoodItem("naan", 3.45, 0))


        restaurant1 = Restaurant("McDonald's", "address1", imagesList, menu1)
        restaurant2 = Restaurant("Wendy's", "address2", imagesList, menu2)
        restaurant3 = Restaurant("Taco Bell", "address3", imagesList, menu3)
        restaurant4 = Restaurant("Five Guys", "address4", imagesList, menu4)
        restaurant5 = Restaurant("Mother Bear's", "address1", imagesList, menu5)
        restaurant6 = Restaurant("Pizza X", "address2", imagesList, menu6)
        restaurant7 = Restaurant("Little Tibet", "address3", imagesList, menu7)
        restaurant8 = Restaurant("Chipotle", "address4", imagesList, menu8)
        restaurant9 = Restaurant("Trojan Horse", "address3", imagesList, menu9)
        restaurant10 = Restaurant("Taste of India", "address4", imagesList, menu10)

        restaurantList = arrayListOf(restaurant1, restaurant2, restaurant3, restaurant4, restaurant5, restaurant6, restaurant7, restaurant8, restaurant9, restaurant10)
        favoritesList = arrayListOf(restaurant5, restaurant4, restaurant8, restaurant7)
    }
}
