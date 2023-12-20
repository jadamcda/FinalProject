package com.example.finalproject

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.util.Date

class HomeFragment : Fragment() {
    lateinit var thisRestaurant: Restaurant
    private lateinit var database: DatabaseReference
    lateinit var thisOrderCard: Order
    var thisOrder: ArrayList<FoodItem> = ArrayList()
    var orderList: ArrayList<Order> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val fViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)
        val rViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        val currentUser = fViewModel.auth.currentUser

        val homePage = view.findViewById<LinearLayout>(R.id.homePage)
        val signUpPage = view.findViewById<ConstraintLayout>(R.id.signUpPage)
        val restaurantPage = view.findViewById<LinearLayout>(R.id.restaurantPage)
        val checkoutPage = view.findViewById<LinearLayout>(R.id.checkoutPage)
        val orderPage = view.findViewById<LinearLayout>(R.id.orderPage)
        val mapPage = view.findViewById<LinearLayout>(R.id.mapPage)
        val recentOrdersPage = view.findViewById<ConstraintLayout>(R.id.recentOrdersPage)
        val calenderPage =  view.findViewById<LinearLayout>(R.id.calenderPage)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signUpButton = view.findViewById<Button>(R.id.signUpButton)
        val checkOutButton = view.findViewById<Button>(R.id.checkOutButton)
        val modifyOrderButton = view.findViewById<Button>(R.id.modifyOrderButton)
        val placeOrderButton = view.findViewById<Button>(R.id.placeOrderButton)
        val trackOrderButton = view.findViewById<Button>(R.id.trackOrderButton)
        val recentOrdersButton1 = view.findViewById<Button>(R.id.recentOrdersButton1)
        val recentOrdersButton2 = view.findViewById<Button>(R.id.recentOrdersButton2)
        val homeButton1 = view.findViewById<Button>(R.id.homeButton1)
        val homeButton2 = view.findViewById<Button>(R.id.homeButton2)
        val calenderButton1 = view.findViewById<Button>(R.id.calenderButton1)
        val calenderButton2 = view.findViewById<Button>(R.id.calenderButton2)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<EditText>(R.id.passwordEditText)
        val deliveryAddressEditText = view.findViewById<EditText>(R.id.deliveryAddressEditText)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        database = Firebase.database.reference

        //Check if user is logged in
        //-----------------------------

        if (currentUser == null) {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.VISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE
            //view.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSignUpFragment())
        }
        else{
            homePage.visibility = View.VISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE
        }

        imageView.setOnClickListener {
//            val pickImageIntent = Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.INTERNAL_CONTENT_URI
//            )
//                pickImageIntent.type = “image”
//                val mimeTypes = arrayOf(“image/jpeg”, “image/png”)
//                pickImageIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//                startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)
            }



        //My navigation isn't working and I can't figure it out so I'm doing this in a really gross way
        //Bare with me

        //Sign in screen
        //-----------------------------

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val userName = nameEditText.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(activity, "You're logged in!", Toast.LENGTH_LONG).show()

                fViewModel.userName = userName
                homePage.visibility = View.VISIBLE
                signUpPage.visibility = View.INVISIBLE
                restaurantPage.visibility = View.INVISIBLE
                checkoutPage.visibility = View.INVISIBLE
                orderPage.visibility = View.INVISIBLE
                mapPage.visibility = View.INVISIBLE
                recentOrdersPage.visibility = View.INVISIBLE
                calenderPage.visibility = View.INVISIBLE

            }.addOnFailureListener {
                Toast.makeText(activity, "Login failure", Toast.LENGTH_LONG).show()
            }
        }

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val userName = nameEditText.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(activity, "You're signed up!", Toast.LENGTH_LONG).show()

                fViewModel.userName = userName
                homePage.visibility = View.VISIBLE
                signUpPage.visibility = View.INVISIBLE
                restaurantPage.visibility = View.INVISIBLE
                checkoutPage.visibility = View.INVISIBLE
                orderPage.visibility = View.INVISIBLE
                mapPage.visibility = View.INVISIBLE
                recentOrdersPage.visibility = View.INVISIBLE
                calenderPage.visibility = View.INVISIBLE

            }.addOnFailureListener {
                Toast.makeText(activity, "Sign up failure", Toast.LENGTH_LONG).show()
            }
        }

        //Home page
        //Top half
        //-----------------------------

        val recyclerH = view.findViewById<RecyclerView>(R.id.recyclerHorizontal)
        recyclerH.setHasFixedSize(true)
        val layoutH = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        recyclerH.layoutManager = layoutH

        val rListH = fViewModel.favoritesList
        database.child("restaurants").child("favoriteRestaurants").setValue(rListH)

        val rAdapterH = RAdapterH(rListH)
        recyclerH.adapter = rAdapterH

        rAdapterH.onButtonClick = {
            rViewModel.thisRestaurant = it

            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.VISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            //Restaurant page
            //-----------------------------

            thisRestaurant = it

            val imagesRecycler = view.findViewById<RecyclerView>(R.id.imagesRecycler)
            imagesRecycler.setHasFixedSize(true)
            val layoutI = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            imagesRecycler.layoutManager = layoutI

            val images = thisRestaurant.images

            val imagesAdaptor = ImagesAdapter(images)
            imagesRecycler.adapter = imagesAdaptor


            val menuRecycler = view.findViewById<RecyclerView>(R.id.menuRecycler)
            menuRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            menuRecycler.layoutManager = layoutM

            val menu = thisRestaurant.menu

            val menuAdaptor = MenuAdapter(menu)
            menuRecycler.adapter = menuAdaptor
        }

        //Home page
        //Bottom half
        //-----------------------------

        val recyclerV = view.findViewById<RecyclerView>(R.id.recyclerVertical)
        recyclerV.setHasFixedSize(true)
        val layoutV = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        recyclerV.layoutManager = layoutV

        val rListV = fViewModel.restaurantList
        database.child("restaurants").child("allRestaurants").setValue(rListV)

        val rAdapterV = RAdapterV(rListV)
        recyclerV.adapter = rAdapterV


        rAdapterV.onButtonClick = {
            println("clicked " + it.placeName)
            rViewModel.thisRestaurant = it

            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.VISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            //Restaurant page
            //-----------------------------

            thisRestaurant = it

            val imagesRecycler = view.findViewById<RecyclerView>(R.id.imagesRecycler)
            imagesRecycler.setHasFixedSize(true)
            val layoutI = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
            imagesRecycler.layoutManager = layoutI

            val images = thisRestaurant.images

            val imagesAdaptor = ImagesAdapter(images)
            imagesRecycler.adapter = imagesAdaptor


            val menuRecycler = view.findViewById<RecyclerView>(R.id.menuRecycler)
            menuRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            menuRecycler.layoutManager = layoutM

            val menu = thisRestaurant.menu

            val menuAdaptor = MenuAdapter(menu)
            menuRecycler.adapter = menuAdaptor
        }

        //Checkout page
        //-----------------------------

        checkOutButton.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.VISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            thisOrder = arrayListOf()

            for(i in thisRestaurant.menu){
                if(i.quantity > 0){
                    println(i.quantity.toString() + " > 0")
                    thisOrder.add(i)
                }
            }

            val checkoutRecycler = view.findViewById<RecyclerView>(R.id.checkoutRecycler)

            checkoutRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            checkoutRecycler.layoutManager = layoutM

            val checkoutAdaptor = MenuAdapter(thisOrder)
            checkoutRecycler.adapter = checkoutAdaptor

            val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val removedElement = thisOrder.get(position)
                    val index = thisRestaurant.menu.indexOf(removedElement)
                    thisRestaurant.menu.get(index).quantity = 0
                    thisOrder.removeAt(position)

                    checkoutRecycler.adapter?.notifyItemRemoved(position)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(checkoutRecycler)
        }

        modifyOrderButton.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.VISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            val menuRecycler = view.findViewById<RecyclerView>(R.id.menuRecycler)
            menuRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            menuRecycler.layoutManager = layoutM

            val menu = thisRestaurant.menu

            val menuAdaptor = MenuAdapter(menu)
            menuRecycler.adapter = menuAdaptor
        }

        placeOrderButton.setOnClickListener {
            var total = 0.0

            for(i in thisOrder){
                total += i.itemPrice
            }

            val sdf = SimpleDateFormat("MM-dd-yyyy HH:mm")
            val currentDateAndTime: String = sdf.format(Date())

            val splitDateAndTime = currentDateAndTime.split(" ")
            val date = splitDateAndTime.get(0)
            val time = splitDateAndTime.get(1)

            val address = deliveryAddressEditText.text.toString()

            thisOrderCard = Order(thisRestaurant, address, thisOrder, total, date, time)

            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.VISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            //Order page
            //-----------------------------

            orderList.add(thisOrderCard)
            database.child("orders").child("pastOrders").setValue(orderList)

            val thisOrderList : ArrayList<Order> = arrayListOf(thisOrderCard)
            val orderRecycler = view.findViewById<RecyclerView>(R.id.orderRecycler)

            orderRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            orderRecycler.layoutManager = layoutM

            val orderAdaptor = OrderAdapter(thisOrderList)
            orderRecycler.adapter = orderAdaptor


        }

        //Map page
        //-----------------------------

        trackOrderButton.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.VISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE
        }

        //Recent orders page
        //-----------------------------
        recentOrdersButton1.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.VISIBLE
            calenderPage.visibility = View.INVISIBLE

            val recentOrdersRecycler = view.findViewById<RecyclerView>(R.id.recentOrdersRecycler)

            recentOrdersRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            recentOrdersRecycler.layoutManager = layoutM

            val recentOrdersAdaptor = RecentOrdersAdapter(orderList)
            recentOrdersRecycler.adapter = recentOrdersAdaptor

            recentOrdersAdaptor.onOrderClick = {
                homePage.visibility = View.INVISIBLE
                signUpPage.visibility = View.INVISIBLE
                restaurantPage.visibility = View.INVISIBLE
                checkoutPage.visibility = View.VISIBLE
                orderPage.visibility = View.INVISIBLE
                mapPage.visibility = View.INVISIBLE
                recentOrdersPage.visibility = View.INVISIBLE
                calenderPage.visibility = View.INVISIBLE

                val checkoutRecycler = view.findViewById<RecyclerView>(R.id.checkoutRecycler)

                checkoutRecycler.setHasFixedSize(true)
                val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                checkoutRecycler.layoutManager = layoutM

                thisOrder = it.items
                thisRestaurant = it.restaurant

                if(!rAdapterH.thisRList.contains(thisRestaurant)){
                    rAdapterH.thisRList.add(thisRestaurant)
                }

                val checkoutAdaptor = MenuAdapter(thisOrder)
                checkoutRecycler.adapter = checkoutAdaptor

                val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        thisOrder.removeAt(position)
                        checkoutRecycler.adapter?.notifyItemRemoved(position)
                    }
                }

                val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
                itemTouchHelper.attachToRecyclerView(checkoutRecycler)
            }
        }

        homeButton1.setOnClickListener {
            homePage.visibility = View.VISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            val rListH = fViewModel.favoritesList
            database.child("restaurants").child("favoriteRestaurants").setValue(rListH)

            val rAdapterH = RAdapterH(rListH)
            recyclerH.adapter = rAdapterH
        }

        recentOrdersButton2.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.VISIBLE
            calenderPage.visibility = View.INVISIBLE

            val recentOrdersRecycler = view.findViewById<RecyclerView>(R.id.recentOrdersRecycler)

            recentOrdersRecycler.setHasFixedSize(true)
            val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            recentOrdersRecycler.layoutManager = layoutM

            val recentOrdersAdaptor = RecentOrdersAdapter(orderList)
            recentOrdersRecycler.adapter = recentOrdersAdaptor

            recentOrdersAdaptor.onOrderClick = {
                homePage.visibility = View.INVISIBLE
                signUpPage.visibility = View.INVISIBLE
                restaurantPage.visibility = View.INVISIBLE
                checkoutPage.visibility = View.VISIBLE
                orderPage.visibility = View.INVISIBLE
                mapPage.visibility = View.INVISIBLE
                recentOrdersPage.visibility = View.INVISIBLE
                calenderPage.visibility = View.INVISIBLE

                val checkoutRecycler = view.findViewById<RecyclerView>(R.id.checkoutRecycler)

                checkoutRecycler.setHasFixedSize(true)
                val layoutM = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
                checkoutRecycler.layoutManager = layoutM

                thisOrder = it.items
                thisRestaurant = it.restaurant

                if(!rAdapterH.thisRList.contains(thisRestaurant)){
                    rAdapterH.thisRList.add(thisRestaurant)
                }

                val checkoutAdaptor = MenuAdapter(thisOrder)
                checkoutRecycler.adapter = checkoutAdaptor

                val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position = viewHolder.adapterPosition
                        thisOrder.removeAt(position)
                        checkoutRecycler.adapter?.notifyItemRemoved(position)
                    }
                }

                val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
                itemTouchHelper.attachToRecyclerView(checkoutRecycler)
            }
        }

        homeButton2.setOnClickListener {
            homePage.visibility = View.VISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.INVISIBLE

            val rListH = fViewModel.favoritesList
            database.child("restaurants").child("favoriteRestaurants").setValue(rListH)

            val rAdapterH = RAdapterH(rListH)
            recyclerH.adapter = rAdapterH
        }

        calenderButton1.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.VISIBLE
        }

        calenderButton2.setOnClickListener {
            homePage.visibility = View.INVISIBLE
            signUpPage.visibility = View.INVISIBLE
            restaurantPage.visibility = View.INVISIBLE
            checkoutPage.visibility = View.INVISIBLE
            orderPage.visibility = View.INVISIBLE
            mapPage.visibility = View.INVISIBLE
            recentOrdersPage.visibility = View.INVISIBLE
            calenderPage.visibility = View.VISIBLE
        }

        return view
    }
}