package com.example.mynoteapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mynoteapp.model.InforNote


class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        const val TABLE_NOTE = "tblNote"
        const val COLUMN_ID = "ID"
        const val COLUMN_TITLE = "nTitle"
        const val COLUMN_CONTENT = "nContent"
        const val COLUMN_DATE = "nDate"
        const val COLUMN_COLOR = "nColor"
        const val COLUMN_PICTURE = "bPicture"
        private const val DATABASE_NAME = "DB_NOTE.db"
        private const val DATABASE_VERSION =1
    }
    // To access your database, instantiate your subclass of SQLiteOpenHelper:
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable: String = " CREATE TABLE " + TABLE_NOTE +
                " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_COLOR + " INTEGER, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_PICTURE + " BLOB )";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS " + TABLE_NOTE
        db?.execSQL(query)
    }


    fun addNote(title: String, content: String, time: String, color: Int ,image: ByteArray? = null)  {
        val database: SQLiteDatabase? = writableDatabase
        val values: ContentValues = ContentValues().apply {
            put(COLUMN_TITLE,title)
            put(COLUMN_CONTENT,content)
            put(COLUMN_DATE,time)
            put(COLUMN_COLOR,color)
            put(COLUMN_PICTURE,image)
        }
        database?.let {
            database.insert(TABLE_NOTE,null,values)
          }

    }
    fun getData(): List<InforNote>? {
        val list = mutableListOf<InforNote>()
        val database = readableDatabase

       val cursor = database.rawQuery("SELECT * FROM " + TABLE_NOTE,null)
        with(cursor) {
            moveToFirst()
            while (isAfterLast == false) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val content = getString(getColumnIndexOrThrow(COLUMN_CONTENT))
                val date = getString(getColumnIndexOrThrow(COLUMN_DATE))
                val color = getInt(getColumnIndexOrThrow(COLUMN_COLOR))
                val img = getBlob(getColumnIndexOrThrow(COLUMN_PICTURE))
                list.add(InforNote(id, title, content, date, color,  img))
                moveToNext()
            }
        }
        return list
    }

    fun removeItem(id: Int): Boolean{
        val db = writableDatabase
       return  db.delete(TABLE_NOTE, COLUMN_ID + "=?", arrayOf<String>(id.toString())) > 0
    }
    fun updateItem(id:Int,title: String, content: String,time: String, color: Int, img:ByteArray? = null) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TITLE,title)
        contentValues.put(COLUMN_CONTENT,content)
        contentValues.put(COLUMN_DATE,time)
        contentValues.put(COLUMN_COLOR,color)
        contentValues.put(COLUMN_PICTURE,img)
        val db = this.writableDatabase
        db.update(TABLE_NOTE,contentValues,COLUMN_ID + "=?", arrayOf<String>(id.toString()))
    }




}