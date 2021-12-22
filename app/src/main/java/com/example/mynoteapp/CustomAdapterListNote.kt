package com.example.mynoteapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynoteapp.model.InforNote
import kotlinx.android.synthetic.main.item_row.view.*

class CustomAdapterListNote(private val context: Context,private val list:List<InforNote> ): RecyclerView.Adapter<CustomAdapterListNote.ViewHolder>() {
    var onItemClick: ((Int,Int) -> Unit)? = null
   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txvTitle = view.textview_title_row as TextView
        val txvContent = view.textview_content_row as TextView
        val txvTime = view.txvTimeName as TextView
        val carView = view.cardViewNote as CardView
        val layoutcardview = view.layoutcardview
        val txvTitleName = view.txvTitleName
        val txvContentName = view.txvContentName
        val txvTimeName = view.txvTimeName
        val imageViewCapture = view.image_view_row
        val checkbox = view.checkbox_delete

//        init {
//            itemView.setOnClickListener {
//                onItemClick?.invoke(list[adapterPosition])
//            }
        //}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list.get(position).color == Color.parseColor("#FF000000")
            || list.get(position).color == Color.parseColor("#FF018786")
            || list.get(position).color == Color.parseColor("#F63A3A")
            || list.get(position).color == Color.parseColor("#FF6200EE")
           ) {
            holder.txvTitle.setTextColor(Color.WHITE)
            holder.txvContent.setTextColor(Color.WHITE)
            holder.txvTime.setTextColor(Color.WHITE)
            holder.txvContentName.setTextColor(Color.WHITE)
            holder.txvTimeName.setTextColor(Color.WHITE)
            holder.txvTitleName.setTextColor(Color.WHITE)
            holder.layoutcardview.setBackgroundResource(R.drawable.custom_cardview)

        }
        holder.txvTitle.text = list.get(position).title
        holder.txvContent.text = list[position].content
        holder.txvTime.text = list.get(position).time
        holder.layoutcardview.setBackgroundColor(list.get(position).color)
        list[position].picture?.let {
            holder.imageViewCapture.visibility = View.VISIBLE
            val bitmap = BitmapFactory.decodeByteArray(it, 0,it.size)
            holder.imageViewCapture.setImageBitmap(bitmap)
        }
        holder.layoutcardview.setOnClickListener {
            val intent = Intent(context,MainActivity3::class.java)
            intent.putExtra("title",list.get(position))
            intent.putExtra("type",1)
            context.startActivity(intent)
        }

        holder.layoutcardview.setOnLongClickListener {
          val builder: AlertDialog? = context?.let {
              val builder = AlertDialog.Builder(it)
              builder.apply {
                  setMessage("Do you want to remove this Note ?")
                  setTitle("Alert Delete")
                  setPositiveButton("Co", DialogInterface.OnClickListener { dialogInterface, i ->
                      list.get(position).id?.let {
                          onItemClick?.invoke(list.get(position).id,position)
                      }
                  })
                  setNegativeButton("Khong", DialogInterface.OnClickListener { dialogInterface, i ->
                      dialogInterface.dismiss()
                  })
              }
              builder.create()

          }
            builder?.show()

            true
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}