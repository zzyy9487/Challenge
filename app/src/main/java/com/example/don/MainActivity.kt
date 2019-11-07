package com.example.don

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.don.Shop.Magic
import com.example.don.Shop.SQLiteHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textViewMoney
import kotlinx.android.synthetic.main.activity_shop.*

class MainActivity : AppCompatActivity() {

    lateinit var sqliteDB:SQLiteDatabase
    var initList = mutableListOf<Magic>()
    var money :Int = 0

    var photoArray = arrayOf(
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12
        )

    var nameArray = arrayOf(
        "Wind (L1)",
        "Bolt (L1)",
        "DePosion (L1)",
        "DeWeight (L1)",
        "Detetion (L1)",
        "FireArrow (L1)",
        "Heal (L1)",
        "HollyWeapen (L1)",
        "IceDagger (L1)",
        "Light (L1)",
        "Shield (L1)",
        "Teleport (L1)",
        "Touch (L1)",
        "Wind (L2)",
        "Bolt (L2)",
        "DePosion (L2)",
        "DeWeight (L2)",
        "Detetion (L2)",
        "FireArrow (L2)",
        "Heal (L2)",
        "HollyWeapen (L2)",
        "IceDagger (L2)",
        "Light (L2)",
        "Shield (L2)",
        "Teleport (L2)",
        "Touch (L2)",
        "Wind (L3)",
        "Bolt (L3)",
        "DePosion (L3)",
        "DeWeight (L3)",
        "Detetion (L3)",
        "FireArrow (L3)",
        "Heal (L3)",
        "HollyWeapen (L3)",
        "IceDagger (L3)",
        "Light (L3)",
        "Shield (L3)",
        "Teleport (L3)",
        "Touch (L3)"
    )

    var priceArray = arrayOf(
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        100,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        200,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500,
        500
    )

    var levelArray = arrayOf(
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        1,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        2,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3
    )

    fun makeMagicList(){
        for (i in 0..38){
        initList.add(i,Magic(i, photoArray[i], nameArray[i], priceArray[i], levelArray[i]))
        }
    }

    fun addInitList(){
        var db = sqliteDB.rawQuery("SELECT * FROM donTable", null)
        if (db.count == 0){
            // 初始資料建立
            makeMagicList()
            for (i in 0 until initList.size){
                sqliteDB.execSQL("INSERT INTO donTable(position, photo, name, price, level, buy) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, initList[i].photo, initList[i].name, initList[i].price, initList[i].level, initList[i].buy))
            }
            db.close()
        }
        else{
            initList.clear()
            db.moveToFirst()
            for (i in 0..38){
                initList.add(i, Magic(i, db.getInt(1), db.getString(2), db.getInt(3), db.getInt(4), db.getInt(5)))
                db.moveToNext()
            }
            db.close()
        }
    }

    fun callMoney(){
//        val db = sqliteDB.rawQuery("SELECT * FROM donMoney", null)
//        db.moveToFirst()
//        if (db.count == 0){
//            db.close()
//        }
//        else {
//            money = db.getInt(1)
//            db.close()
//        }
        var preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
        var cash = preference.getString("cash", "")

        if (cash.isNullOrEmpty()){
            money = 2000
        }
        else{
            money = cash.toInt()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SQLite初始化
        sqliteDB = SQLiteHelper(this).writableDatabase
        // 判斷SQLite裡是否有之前資料
        addInitList()
        // callMoney
        callMoney()
        // 初始金額
        textViewMoney.text = money.toString()
        // 金額寫入 SharePreference
        val preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
        preference.edit().putString("cash", money.toString()).apply()

        // 金額寫入SQLite
//        sqliteDB.delete("donMoney", null, null)
//        sqliteDB.execSQL("INSERT INTO donMoney(number, cash) VALUES(?,?)", arrayOf<Any?>(0, money))

        // 魔法商店按鈕監聽
        buttonShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
            this@MainActivity.finish()
        }
        // reset
        textViewH.setOnClickListener {
            val preferenceReset = getSharedPreferences("cash", Context.MODE_PRIVATE)
            money = 2000
            preferenceReset.edit().putString("cash", money.toString()).apply()
            textViewMoney.text = money.toString()
            initList.forEach { it.buy = 0 }
            var db = sqliteDB.rawQuery("SELECT * FROM donTable", null)
            sqliteDB.delete("donTable", null, null)
            for (i in 0 until initList.size){
                sqliteDB.execSQL("INSERT INTO donTable(position, photo, name, price, level, buy) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, initList[i].photo, initList[i].name, initList[i].price, initList[i].level, initList[i].buy))
            }
            db.close()
        }
        // +$
        imageViewMoney.setOnClickListener {
            money = money + 100
            textViewMoney.text = money.toString()
            val preferencePlus = getSharedPreferences("cash", Context.MODE_PRIVATE)
            preferencePlus.edit().putString("cash", money.toString()).apply()
        }
        // 魔法列表按鈕監聽
        buttonMagicTotalList.setOnClickListener {
            val intent = Intent(this, MagicActivity::class.java)
            startActivity(intent)
        }

        var inputword : String = ""

        imageBug.setOnClickListener {
            inputword = "$inputword" + "1"
        }

        imageKill.setOnClickListener {
            inputword = "$inputword" + "2"
            if (inputword.contains("1121122")) {
                Toast.makeText(this, "按這麼久才加100...QQ...", Toast.LENGTH_LONG).show()
                inputword = ""
                money = money + 100
                textViewMoney.text = money.toString()
                val preferenceBonus = getSharedPreferences("cash", Context.MODE_PRIVATE)
                preferenceBonus.edit().putString("cash", money.toString()).apply()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        this@MainActivity.finish()
    }
}
