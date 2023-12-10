package com.example.lealapps.ui.exerciseDialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lealapps.R
import com.example.lealapps.model.Exercise

class ExerciseDialogAdapter : RecyclerView.Adapter<ExerciseDialogAdapter.ViewHolder>() {
    private var exercises = arrayListOf<Exercise>()
    lateinit var exerciseListener : ExerciseListener
    lateinit var dialog : ExerciseDialog

    fun updateList(exercises: List<Exercise>) {
        this.exercises.clear()
        this.exercises.addAll(exercises)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_training, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvExercise.text = exercises[position].name
        holder.itemView.setOnClickListener {
            exerciseListener.selectedExercise(exercises[position].name)
            dialog.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvExercise : TextView = itemView.findViewById(R.id.tvExercise)
    }
}