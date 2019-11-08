package com.example.don

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.don.Shop.Magic
import kotlinx.android.synthetic.main.activity_shop.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.don.Shop.ShopAdapter
import com.example.don.Shop.SQLiteHelper
import kotlinx.android.synthetic.main.activity_shop.textViewMoney
import kotlinx.android.synthetic.main.alert_layout.*
import kotlin.random.Random

class ShopActivity : AppCompatActivity() {

    var shopList = mutableListOf<Magic>()
    var fShopList = mutableListOf<Magic>()
    var money:Int = 0

    lateinit var textPrice :TextView
    lateinit var imageView :ImageView
    lateinit var pos :TextView
    lateinit var neg :TextView
    lateinit var bye :TextView

    lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)


        // SQLite
        var sqliteDB = SQLiteHelper(this).writableDatabase

        fun addShopList(){
            val DB = sqliteDB.rawQuery("SELECT * FROM donTable", null)
            shopList.clear()
            DB.moveToFirst()
            for (i in 0 until DB.count){
                shopList.add(i, Magic(i, DB.getInt(1), DB.getString(2), DB.getInt(3), DB.getInt(4), DB.getInt(5)))
                DB.moveToNext()
            }
            DB.close()
        }
        addShopList()

        // getMoney
        val preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
        var currentcash = preference.getString("cash","")
        money = currentcash!!.toInt()
        textViewMoney.text = money.toString()

        // recyclerView layoutManager&Adapter
        fShopList.addAll(shopList.filter { it.level == 1 })
        var adapter = ShopAdapter(fShopList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // interface

        adapter.setclickedListener(object :ShopAdapter.clickedListener{

            override fun showPurchase(img: Int, price: Int, position:Int) {

                var inflater = this@ShopActivity.layoutInflater
                var view = inflater.inflate(R.layout.alert_layout, null)
                var builder = AlertDialog.Builder(adapter.context!!)
                    .setView(view)
                    .show()

                imageView = view.findViewById(R.id.imagePurchase)
                textPrice = view.findViewById(R.id.textPurchasePrice)
                pos = view.findViewById(R.id.textPurchaseBuy)
                neg = view.findViewById(R.id.textPurachseCancel)
                bye = view.findViewById(R.id.textPurachseBye)
                textPrice.text = "$"+"$price"
                imageView.setImageResource(img)
                pos.setOnClickListener {
                    if (money >= price){
                        var aftermoney:Int
                        aftermoney = money - price
                        money = aftermoney
                        shopList[position].buy = 1
                        sqliteDB.delete("donTable", null, null)
                        for (i in 0 until shopList.size){
                            sqliteDB.execSQL("INSERT INTO donTable(position, photo, name, price, level, buy) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, shopList[i].photo, shopList[i].name, shopList[i].price, shopList[i].level, shopList[i].buy))
                        }
                        adapter.notifyDataSetChanged()
                        textViewMoney.text = money.toString()
                        val preference2 = getSharedPreferences("cash", Context.MODE_PRIVATE)
                        preference2.edit().putString("cash", money.toString()).apply()
                        builder.dismiss()
                    }
                    else{
                        var inflaterHaHa = this@ShopActivity.layoutInflater
                        var viewHaHa = inflaterHaHa.inflate(R.layout.haha_layout, null)
                        var builderHaHa = AlertDialog.Builder(adapter.context!!)
                            .setView(viewHaHa)
                            .show()
                        var bye = viewHaHa.findViewById<TextView>(R.id.textBye)
                        builder.dismiss()
                        bye.setOnClickListener {
                            builderHaHa.dismiss()
                        }
                    }
                }

                neg.setOnClickListener {
                    builder.dismiss()
                }

                // 購買按鈕監聽事件
//                textPurchaseBuy.setOnClickListener {
//                    if (money >= price){
//                        var aftermoney:Int
//                        aftermoney = money - price
//                        money = aftermoney
//                        shopList[position].buy = 1
//                        sqliteDB.delete("donTable", null, null)
//                        for (i in 0 until shopList.size){
//                            sqliteDB.execSQL("INSERT INTO donTable(position, photo, name, price, level, buy) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, shopList[i].photo, shopList[i].name, shopList[i].price, shopList[i].level, shopList[i].buy))
//                        }
//                        adapter.notifyDataSetChanged()
//                        textViewMoney.text = money.toString()
//                        val preference2 = getSharedPreferences("cash", Context.MODE_PRIVATE)
//                        preference2.edit().putString("cash", money.toString()).apply()
//                        purchaseLayout.visibility = View.GONE
//                    }
//                    else{
//                        imagePurchase.setImageResource(R.drawable.eyes)
//                        textPurchasePrice.text = "您的錢有帶夠嗎?!"
//                        textPurchaseBuy.visibility = View.GONE
//                        textPurachseCancel.visibility = View.GONE
//                        textPurachseBye.visibility = View.VISIBLE
//                    }
//                }
//
//                // 取消按鈕監聽
//                textPurachseCancel.setOnClickListener {
//                    purchaseLayout.visibility = View.GONE
//                    recyclerView.isEnabled = true
//                }
//
//                // 掰按鈕監聽
//                textPurachseBye.setOnClickListener {
//                    textPurchaseBuy.visibility = View.VISIBLE
//                    textPurachseCancel.visibility = View.VISIBLE
//                    textPurachseBye.visibility = View.GONE
//                    purchaseLayout.visibility = View.GONE
//                }
            }
        })


        textViewL1.setOnClickListener {
            fShopList.clear()
            fShopList.addAll(shopList.filter { it.level == 1 })
            adapter.notifyDataSetChanged()
        }

        textViewL2.setOnClickListener {
            fShopList.clear()
            fShopList.addAll(shopList.filter { it.level == 2 })
            adapter.notifyDataSetChanged()
        }

        textViewL3.setOnClickListener {
            fShopList.clear()
            fShopList.addAll(shopList.filter { it.level == 3 })
            adapter.notifyDataSetChanged()
        }

        imageViewList1.setOnClickListener {
            recyclerView.setBackgroundColor(Color.WHITE)
            adapter.currentType = adapter.viewType_Linear
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter.notifyDataSetChanged()
        }

        imageViewList2.setOnClickListener {
            recyclerView.setBackgroundColor(Color.DKGRAY)
            adapter.currentType = adapter.viewType_Grid
            recyclerView.layoutManager = GridLayoutManager(this, 4)
            adapter.notifyDataSetChanged()
        }

        imageViewm.setOnClickListener {
            money = money - 1000
            textViewMoney.text = money.toString()
            val preferenceMinus = getSharedPreferences("cash", Context.MODE_PRIVATE)
            preferenceMinus.edit().putString("cash", money.toString()).apply()
        }

    }

    // 返回鍵監聽

//    override fun onBackPressed() {
//        super.onBackPressed()
//        if(purchaseLayout.isVisible){
//            textPurchaseBuy.visibility = View.VISIBLE
//            textPurachseCancel.visibility = View.VISIBLE
//            textPurachseBye.visibility = View.GONE
//            purchaseLayout.visibility = View.GONE
//        }
//        else{
//        }
//    }

//    override fun onBackPressed() {
//        if(purchaseLayout.isVisible){
//            textPurchaseBuy.visibility = View.VISIBLE
//            textPurachseCancel.visibility = View.VISIBLE
//            textPurachseBye.visibility = View.GONE
//            purchaseLayout.visibility = View.GONE
//        }
//        else{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            this@ShopActivity.finish()
//        }
//    }


}
