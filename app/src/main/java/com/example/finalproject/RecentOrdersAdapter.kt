package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecentOrdersAdapter (private val orderList: ArrayList<Order>)
    : RecyclerView.Adapter<RecentOrdersAdapter.OrderViewHolder>() {

    var onOrderClick: ((Order) -> Unit)? = null
    var thisOrderList = orderList

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderCard : ConstraintLayout = itemView.findViewById(R.id.orderCard)
        val foodItemText: TextView = itemView.findViewById(R.id.foodItemText)
        val orderedFromText: TextView = itemView.findViewById(R.id.orderedFromText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
        val addressText: TextView = itemView.findViewById(R.id.addressText)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val thisOrder = thisOrderList[position]

        var foodItemText = ""
        var quantityText = ""

        for(i in thisOrder.items){

            if(foodItemText != ""){
                foodItemText += "\n"
                quantityText += "\nQuantity: "
            }

            foodItemText += i.itemName
            quantityText += i.quantity.toString()
        }

        holder.foodItemText.text = foodItemText
        holder.orderedFromText.text = "Ordered from: " + thisOrder.restaurant.placeName
        holder.priceText.text = "Price: $" + thisOrder.total.toString()
        holder.dateText.text = "Date: " + thisOrder.date
        holder.addressText.text = "Address: " + thisOrder.address
        holder.quantityText.text = "Quantity: $quantityText"
        holder.timeText.text = "Time: " + thisOrder.time

        holder.orderCard.setOnClickListener{
            onOrderClick?.invoke(thisOrder)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}