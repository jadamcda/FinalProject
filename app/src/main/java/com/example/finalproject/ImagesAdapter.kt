package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.media.Image

class ImagesAdapter(private val imagesList:ArrayList<Int>)
    : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    var thisImageList = imagesList

    class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.images_view, parent, false)
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val thisImage = thisImageList[position]

        holder.imageView.setImageResource(thisImage)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}