package com.example.lealapps.ui.exerciseDialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lealapps.R
import com.example.lealapps.model.Exercise
import kotlin.math.roundToInt

class ExerciseDialog(
    private val activity: Activity,
    private val exerciseListener: ExerciseListener,
    private val exercises: List<Exercise>,
): Dialog(activity) {

    private val adapter = ExerciseDialogAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_exercise)
        setupView()
        setupRecyclerView()
        onCloseClick()
    }

    private fun setupView() {
        this.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE));
        val size = Point()
        this.window?.decorView?.setBackgroundResource(R.drawable.shape_round)
        activity.windowManager.defaultDisplay.getSize(size)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window!!.attributes)
        lp.width = (size.x * 0.9f).roundToInt()

        window!!.attributes = lp
    }

    private fun setupRecyclerView() {
        val rvExercises = findViewById<RecyclerView>(R.id.rvExercises)
        val layoutManager = LinearLayoutManager(activity)
        rvExercises.layoutManager = layoutManager
        rvExercises.adapter = adapter
        adapter.exerciseListener = exerciseListener
        adapter.updateList(exercises)
        adapter.dialog = this
    }

    private fun onCloseClick() {
        val ivClose = findViewById<ImageView>(R.id.ivClose)
        ivClose.setOnClickListener {
            dismiss()
        }
    }
}