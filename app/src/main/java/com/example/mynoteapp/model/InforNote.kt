package com.example.mynoteapp.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
@Parcelize
data  class InforNote(val id: Int = -1,
                      val title:String,
                      val content: String,
                      var time:String,
                      val color: Int,
                      val picture: ByteArray? = null) : Parcelable {

}



