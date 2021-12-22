package com.example.mynoteapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.MenuInflater
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import com.example.mynoteapp.database.DbHelper
import com.example.mynoteapp.getdatafrominterface.GetColorInterface
import com.example.mynoteapp.model.InforNote
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet_main3.*
import kotlinx.android.synthetic.main.custom_activity_main3.*
import kotlinx.android.synthetic.main.custom_activity_main3.txvTimeName
import kotlinx.android.synthetic.main.item_row.*
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class MainActivity3() :AppCompatActivity(), GetColorInterface {
    private  val REQUEST_IMAGE_CAPTURE = 1
    private  val MY_CAMERA_PERMISSION_CODE = 100
    var dbHelper = DbHelper(this)
    var id: Int? = null
    private var getcolor: Int = Color.parseColor("#EFDCDC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        txvTimeName.text =
            SimpleDateFormat("yyy/MM/dd HH:mm:ss").format(Calendar.getInstance().time)
        imageColorNote.setOnClickListener {
            CustomBottomSheetMain3(this, this@MainActivity3).show(
                supportFragmentManager,
                CustomBottomSheetMain3.TAG
            )
        }
        imgUpButtonMain3.setOnClickListener { onBackPressed() }
        imgSaveNote.setOnClickListener {
            if (invalid()) {
                if(imageViewCamera.visibility == View.INVISIBLE) {
                    saveNote()
                    startActivity(Intent(this, MainActivity2::class.java))
                } else {
                    saveNotewitImg()
                    startActivity(Intent(this, MainActivity2::class.java))
                }
            }
            else {
                Snackbar.make(imgSaveNote, "Missing some field!!", Snackbar.LENGTH_SHORT).show()
            }

        }
        imgMoreVert.setOnClickListener { popUpmenu(it) }
        showNote()
    }

    private fun popUpmenu(view: View) {
        val popup = PopupMenu(this, view).apply {
            menuInflater.inflate(R.menu.popup_main3, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    // R.id.item_mic -> TODO()
                    R.id.item_camera -> takePicturefromItent()
                    /*  R.id.item_edit -> TODO()
                      R.id.item_remove -> TODO()*/

                }
                false
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true)
        } else {
            try {
                val fields = popup.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[popup]
                        val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons: Method = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        popup.show()


    }


    override fun getDataColor(color: Int) {
        getcolor = color
        imageColorNote.setColorFilter(getcolor)
    }

    fun saveNote() {
        if(intent.getIntExtra("type",0) == 1) {
            dbHelper.updateItem(id!!,
                edtTitle.text.toString(),
                edtContent.text.toString(),
                txvTimeName.text.toString(),
                getcolor)
        }
        else {
            dbHelper.addNote(
                edtTitle.text.toString(),
                edtContent.text.toString(),
                txvTimeName.text.toString(),
                getcolor
            )
        }
    }
    fun saveNotewitImg() {
            val bitmapdrawable:BitmapDrawable = imageViewCamera.drawable as BitmapDrawable
            var bitmap = bitmapdrawable.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,1,stream)
            val data = stream.toByteArray()
        if(intent.getIntExtra("type",0) == 1) {
            dbHelper.updateItem(id!!,
                edtTitle.text.toString(),
                edtContent.text.toString(),
                txvTimeName.text.toString(),
                getcolor,data)
        }
        else {
            dbHelper.addNote(
                edtTitle.text.toString(),
                edtContent.text.toString(),
                txvTimeName.text.toString(),
                getcolor,
                data
            )
        }

    }

    fun invalid(): Boolean {

        if (edtTitle.text.toString().equals("")) {
            return false
        }
        if (edtContent.text.toString().equals("")) {
            return false
        }
        return true
    }

    private fun takePicturefromItent() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this,"Can not open camera",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewCamera.visibility = View.VISIBLE
            imageViewCamera.setImageBitmap(imageBitmap)
        }
    }
     fun showNote() {
         val inforNote: InforNote? = intent.getParcelableExtra("title")
         inforNote?.let { inforNote ->
             id = inforNote.id
             txvTimeName.text = inforNote.time.toString()
             edtTitle.setText(inforNote.title)
             edtContent.setText(inforNote.content)
             imageColorNote.setColorFilter(inforNote.color)
                 inforNote?.picture?.let{
                     val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                     imageViewCamera.visibility = View.VISIBLE
                     imageViewCamera.setImageBitmap(bitmap)
                 }
         }

         }

}

