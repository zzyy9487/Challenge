package com.example.don.Shop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.don.R
import kotlinx.android.synthetic.main.cell_magic_grid.view.*
import kotlinx.android.synthetic.main.cell_magic_linear.view.*
import kotlinx.android.synthetic.main.cell_magic_linear.view.imageView
import kotlinx.android.synthetic.main.cell_magic_linear.view.textViewName

class ShopAdapter(var list:List<Magic>, val context: Context?):RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    val viewType_Linear = 1
    val viewType_Grid = 0
    var currentType = 1

    // 點擊監聽
    private var itemClickListener:clickedListener? = null

    interface clickedListener{
        fun showPurchase(img:Int, price:Int, position: Int)
    }

    fun setclickedListener(checkedListener:clickedListener){
        this.itemClickListener = checkedListener
    }

    override fun getItemViewType(position: Int): Int {
        when(currentType){
            1 -> return viewType_Linear
            else -> return viewType_Grid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when(viewType){
            viewType_Linear ->{
                val viewLinear = LayoutInflater.from(context).inflate(R.layout.cell_magic_linear, parent, false)
                return ViewHolder(viewLinear, viewType)
            }
            else -> {
                val viewGrid = LayoutInflater.from(context).inflate(R.layout.cell_magic_grid, parent, false)
                return ViewHolder(viewGrid, viewType)
            }
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindViewHolder(list[position])

    }

    inner class ViewHolder(itemView:View, var viewType: Int):RecyclerView.ViewHolder(itemView){


        fun bindViewHolder(list:Magic){

            when(viewType){
                viewType_Linear ->{
                    var viewsoldout = itemView.viewSoldout
                    var textsoldout = itemView.textSoldout
                    var imgView = itemView.imageView
                    imgView.setImageResource(list.photo)
                    var textName = itemView.textViewName
                    textName.text = list.name
                    var textPrice = itemView.textViewPrice
                    textPrice.text = list.price.toString()
                    if (list.buy == 1){
                        viewsoldout.visibility = View.VISIBLE
                        textsoldout.visibility = View.VISIBLE
                        itemView.setOnClickListener {

                        }
                    }
                    else{
                        viewsoldout.visibility = View.GONE
                        textsoldout.visibility = View.GONE
                        itemView.setOnClickListener {
                            itemClickListener?.showPurchase(list.photo, list.price, list.position)
                        }
                    }
                }
                else ->{
                    var viewsold = itemView.viewSold
                    var textsold = itemView.textSold
                    var imgViewGrid = itemView.imageViewGrid
                    imgViewGrid.setImageResource(list.photo)
                    if (list.buy == 1){
                        viewsold.visibility = View.VISIBLE
                        textsold.visibility = View.VISIBLE
                        itemView.setOnClickListener {
                        }
                    }
                    else{
                        viewsold.visibility = View.GONE
                        textsold.visibility = View.GONE
                        itemView.setOnClickListener {
                            itemClickListener?.showPurchase(list.photo, list.price, list.position)
                        }
                    }
                }
            }
        }
    }
}
