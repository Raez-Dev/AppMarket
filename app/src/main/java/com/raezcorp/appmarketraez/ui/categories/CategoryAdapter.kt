package com.raezcorp.appmarketraez.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raezcorp.appmarketraez.R
import com.raezcorp.appmarketraez.databinding.ItemCategoriesBinding
import com.raezcorp.appmarketraez.model.Category
import com.squareup.picasso.Picasso

// 1.- Define data place
// 3.- Adapter methods
class CategoryAdapter(var categories: List<Category> = listOf(),
                      var onClickCategory:(Category) -> Unit) :RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder>() {

//    lateinit var onClickCategory:(Category) -> Unit
    // 2. Define an internal viewHolder class
    // XML (item)
    // Data
    inner class CategoryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemCategoriesBinding = ItemCategoriesBinding.bind(itemView)

        fun bind(category: Category) = with(binding) {
            Picasso.get().load(category.cover).into(imgCategories)

            imgCategories.setOnClickListener {
                onClickCategory(category)
            }
        }
    }

    fun updateCategories(categories:List<Category>){
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        // Create a view
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_categories,parent,false)
        return CategoryAdapterViewHolder(view)

    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        // Get Data
        val category = categories[position]
        holder.bind(category)

    }

    override fun getItemCount(): Int {
        //  Get a list size
        return categories.size
    }
}