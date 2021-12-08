package com.example.to_do

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter (private val itemsList:List<Items>, listenerContext: OnItemClickListener): RecyclerView.Adapter<ItemsViewHolder>()
{
    private var myInterface: OnItemClickListener = listenerContext
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        return ItemsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item : Items = itemsList.get(position)
        holder.bind(item)

        holder.itemView.setOnClickListener{
            myInterface.itemClick(position)
        }

        holder.itemView.setOnLongClickListener() {
            myInterface.itemLongClick(position)
            true
        }
    }

}