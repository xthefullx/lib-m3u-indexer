package com.thefull.m3uindexer

import android.database.sqlite.SQLiteDatabase
import com.thefull.m3uparser.M3UItem

class IndexDatabaseWriter(private val db: SQLiteDatabase) {

    fun insertLive(list: List<M3UItem>) {
        db.beginTransaction()
        try {
            val stmt = db.compileStatement(
                "INSERT INTO live(name,url,logo,category) VALUES(?,?,?,?)"
            )
            for (i in list) {
                stmt.bindString(1, i.name)
                stmt.bindString(2, i.url)
                stmt.bindString(3, i.logo)
                stmt.bindString(4, i.group)
                stmt.executeInsert()
                stmt.clearBindings()
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}
