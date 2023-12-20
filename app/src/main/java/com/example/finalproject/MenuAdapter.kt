package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val foodList:ArrayList<FoodItem>)
    : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    var thisFoodList = foodList

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plusButton: ImageButton = itemView.findViewById(R.id.plusButton)
        val minusButton: ImageButton = itemView.findViewById(R.id.minusButton)
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_view, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val thisFoodItem = thisFoodList[position]
        holder.itemName.text = thisFoodItem.itemName
        holder.itemPrice.text = "$" + thisFoodItem.itemPrice.toString()
        holder.itemQuantity.text = thisFoodItem.quantity.toString()

        holder.plusButton.setOnClickListener {
            //onPlusClick?.invoke(thisFoodItem)
            thisFoodItem.quantity++

            holder.itemQuantity.text = thisFoodItem.quantity.toString()
        }

        holder.minusButton.setOnClickListener {
            //onMinusClick?.invoke(thisFoodItem)
            if(thisFoodItem.quantity > 0){
                thisFoodItem.quantity--
            }

            holder.itemQuantity.text = thisFoodItem.quantity.toString()
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}
