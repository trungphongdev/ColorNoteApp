package com.example.mynoteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynoteapp.database.DbHelper
import com.example.mynoteapp.model.InforNote
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.item_row.*
import java.io.Serializable
public const val EXTRAS_NOTE ="Note"
class MainActivity2 : AppCompatActivity() {
    val dbHelper = DbHelper(this)
    private  var list: MutableList<InforNote>? = null
    private  lateinit var adapterRecy: CustomAdapterListNote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
         list  = mutableListOf()
        dbHelper.getData()?.let {
            list = dbHelper.getData()?.toMutableList()
            println(list)
        }
         adapterRecy = CustomAdapterListNote(this@MainActivity2,list!!)

        myRecyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity2)
            adapter = adapterRecy

        }


        floating.setOnClickListener {
            startActivity(Intent(this@MainActivity2,MainActivity3::class.java))
        }
      removeItem()

    }
    fun removeItem() {
        adapterRecy.onItemClick = {id: Int, postion:Int ->
            if(dbHelper.removeItem(id)){
                Toast.makeText(this,"Delete Successfully",Toast.LENGTH_SHORT).show()
                list?.removeAt(postion)
                adapterRecy.notifyDataSetChanged()
                }
            else {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show()
            }

        }




    }



   /* fun menuBottomAppbar() {
        botom_appbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.itemRemove -> {
                    removeItem()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }*/



}


