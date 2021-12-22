package com.example.mynoteapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.mynoteapp.getdatafrominterface.GetColorInterface
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_main3.view.*

class CustomBottomSheetMain3(context: Context,private val getColorInterface: GetColorInterface): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_main3, container, false)
        val imgYellow = view.circle_color_yellow as ImageView
        val imgPurple = view.circle_color_purple  as ImageView
        val imgBlack = view.circle_color_black  as ImageView
        val imgTurquoise = view.circle_color_turquoise as ImageView
        val imgViolet = view.circle_color_violet as ImageView

        imgYellow.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#FFEB3B"))
            }
        imgBlack.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#FF000000"))
        }
        imgPurple.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#FF6200EE"))
        }
        imgTurquoise.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#FF03DAC5"))
        }
        imgViolet.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#FFBB86FC"))
        }
        view.circle_color_green.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#4CAF50"))
        }
        view.circle_color_blue.setOnClickListener{
            getColorInterface.getDataColor(Color.parseColor("#248ADB"))
        }
        view.circle_color_red.setOnClickListener {
            getColorInterface.getDataColor(Color.parseColor("#F63A3A"))
        }
        return view
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}
