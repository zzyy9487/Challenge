package com.example.don.Shop

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, name, null, version) {
    companion object {
        private const val name = "sqLite.db" //資料庫名稱
        private const val version = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE donTable(position integer PRIMARY KEY, photo integer NOT NULL, name text NOT NULL, price integer NOT NULL, level integer NOT NULL, buy integer NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //刪除資料表
        db.execSQL("DROP TABLE IF EXISTS donTable")
        //重建資料庫
        onCreate(db)
    }
}
