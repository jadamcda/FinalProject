package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RAdapterH(private val rList:ArrayList<Restaurant>)
    : RecyclerView.Adapter<RAdapterH.RViewHolder>() {

    var onButtonClick: ((Restaurant) -> Unit)? = null
    var thisRList = rList

    class RViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rCard: LinearLayout = itemView.findViewById(R.id.restaurantCardH)
        val textH : TextView = itemView.findViewById(R.id.recyclerTextH)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaraunt_horrizontal_view, parent, false)
        return RViewHolder(view)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        val restaurant = thisRList[position]
        holder.textH.text = restaurant.placeName

        holder.rCard.setOnClickListener{
            onButtonClick?.invoke(restaurant)
        }
    }

    override fun getItemCount(): Int {
        return rList.size
    }
}