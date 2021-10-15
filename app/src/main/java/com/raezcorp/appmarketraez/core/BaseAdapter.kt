package com.raezcorp.appmarketraez.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raezcorp.appmarketraez.model.Category

abstract class BaseAdapter<T> (var data: List<T>) :RecyclerView.Adapter<BaseAdapter.BaseAdapterViewHolder<T>>() {

    //    lateinit var onClickCategory:(Category) -> Unit
    // 2. Define an internal viewHolder class
    // XML (item)
    // Data
    abstract class BaseAdapterViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(entity:T)
    }

    fun update(nData:List<T>){
        this.data = nData
        notifyDataSetChanged()
    }
    fun reset(){
        data = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapterViewHolder<T> {
        return getViewHolder(parent)
    }

    abstract fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<T>

    override fun onBindViewHolder(holder: BaseAdapterViewHolder<T>, position: Int) {

        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        //  Get a list size
        return data.size
    }
}