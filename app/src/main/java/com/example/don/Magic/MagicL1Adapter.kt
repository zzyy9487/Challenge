package com.example.don.Magic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.don.R
import com.example.don.Shop.Magic
import kotlinx.android.synthetic.main.cell_magic_grid.view.*

class MagicL1Adapter(var list:List<Magic>, val context: Context?):RecyclerView.Adapter<MagicL1Adapter.ViewHolder>() {

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

        val viewGrid = LayoutInflater.from(context).inflate(R.layout.cell_magic_grid, parent, false)
        return ViewHolder(viewGrid)

    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])

    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        fun bindViewHolder(list:Magic){

            itemView.imageViewGrid.setImageResource(list.photo)
            if (list.buy == 0){itemView.imageViewGrid.alpha = 0.2.toFloat()}
            else{itemView.imageViewGrid.alpha = 1.0.toFloat()}

        }

    }
}
