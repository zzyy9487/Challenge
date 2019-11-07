package com.example.don.Shop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.don.R
import kotlinx.android.synthetic.main.cell_magic_grid.view.*
import kotlinx.android.synthetic.main.cell_magic_linear.view.*

class GridAdapter(var list:List<Magic>, val context: Context?):RecyclerView.Adapter<GridAdapter.ViewHolder>() {

//    private var itemClickListener:clickedListener? = null
//
//    interface clickedListener{
//        fun saveQueryWords()
//    }
//
//    fun setclickedListener(checkedListener:clickedListener){
//        this.itemClickListener = checkedListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cell_magic_grid, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])

    }
    
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val imgView = itemView.imageViewGrid

        fun bindViewHolder(list:Magic){

            imgView.setImageResource(list.photo)

        }
    }
}
