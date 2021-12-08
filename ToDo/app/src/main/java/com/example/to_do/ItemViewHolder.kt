package com.example.to_do

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder class ItemsViewHolder(inflater: LayoutInflater, parent: ViewGroup)
    : RecyclerView.ViewHolder (inflater.inflate(R.layout.item_row,parent,false))

{
    private var itemsNameTextView: TextView? = null
    private var itemCheckBox: ImageView? = null


    init
    {
        itemsNameTextView = itemView.findViewById(R.id.itemNameTextView)
    }

    fun bind(item: Items)
    {
        itemsNameTextView!!.text = item.name
        if(item.completed){
            itemView.setBackgroundColor(Color.parseColor("#141A39"));
            itemsNameTextView!!.paintFlags = itemsNameTextView!!.paintFlags or
                    Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            itemView.setBackgroundColor(Color.parseColor("#38489C"));
            itemsNameTextView!!.paintFlags = itemsNameTextView!!.paintFlags and
                    Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}