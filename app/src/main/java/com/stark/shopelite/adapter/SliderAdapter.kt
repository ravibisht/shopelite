package com.stark.shopelite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stark.shopelite.R
import com.stark.shopelite.model.Slider

class SliderAdapter(private val listOfSliders: MutableList<Slider>) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.slide_banner_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sliderIV.setImageResource(listOfSliders[position].banner)
    }

    override fun getItemCount(): Int = listOfSliders.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sliderIV = itemView.findViewById<ImageView>(R.id.slider_banner_item)
    }
}