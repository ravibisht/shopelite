package com.stark.shopelite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stark.shopelite.R
import com.stark.shopelite.model.Category

class CategoryAdapter(private val categoryModelList : MutableList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryIcon.setImageResource(categoryModelList[position].categoryIconUrl)
        holder.categoryName.text = categoryModelList[position].categoryName
    }

    override fun getItemCount(): Int {
        return categoryModelList.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val categoryIcon: ImageView = itemView.findViewById(R.id.category_list_icon)
        val categoryName: TextView = itemView.findViewById(R.id.category_list_category_name)
    }
}