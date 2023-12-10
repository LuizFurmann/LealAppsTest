package com.example.lealapps.ui.infoDialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.lealapps.R
import kotlin.math.roundToInt

class InfoDialog(
    private val activity: Activity
): Dialog(activity){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_info)

        setupView()
    }

    private fun setupView(){
        this.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE));
        val size = Point()
        this.window?.decorView?.setBackgroundResource(R.drawable.shape_round)
        activity.windowManager.defaultDisplay.getSize(size)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window!!.attributes)
        lp.width = (size.x * 0.9f).roundToInt()

        window!!.attributes = lp
    }
}