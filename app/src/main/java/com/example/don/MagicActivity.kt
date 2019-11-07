package com.example.don

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.don.Magic.MagicL1Adapter
import com.example.don.Magic.MagicL2Adapter
import com.example.don.Magic.MagicL3Adapter
import com.example.don.Shop.Magic
import com.example.don.Shop.SQLiteHelper
import kotlinx.android.synthetic.main.activity_magic.*

class MagicActivity : AppCompatActivity() {

    var magicList = mutableListOf<Magic>()
    var inputL1List = mutableListOf<Magic>()
    var inputL2List = mutableListOf<Magic>()
    var inputL3List = mutableListOf<Magic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic)

        var sqliteDB = SQLiteHelper(this).writableDatabase
        val DB = sqliteDB.rawQuery("SELECT * FROM donTable", null)
        fun addMagicList(){
            magicList.clear()
            DB.moveToFirst()
            for (i in 0 until DB.count){
                magicList.add(i, Magic(i, DB.getInt(1), DB.getString(2), DB.getInt(3), DB.getInt(4), DB.getInt(5)))
                DB.moveToNext()
            }
        }

        addMagicList()

        inputL1List.addAll(magicList.filter { it.level == 1 })
        inputL2List.addAll(magicList.filter { it.level == 2 })
        inputL3List.addAll(magicList.filter { it.level == 3 })

        recyclerL1.layoutManager = GridLayoutManager(this, 4)
        recyclerL2.layoutManager = GridLayoutManager(this, 4)
        recyclerL3.layoutManager = GridLayoutManager(this, 4)

        var l1Adapter = MagicL1Adapter(inputL1List, this)
        var l2Adapter = MagicL2Adapter(inputL2List, this)
        var l3Adapter = MagicL3Adapter(inputL3List, this)

        recyclerL1.adapter = l1Adapter
        recyclerL2.adapter = l2Adapter
        recyclerL3.adapter = l3Adapter



    }
}
