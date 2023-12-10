package com.example.lealapps.ui.exercise

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lealapps.databinding.RowExerciseBinding
import com.example.lealapps.model.Exercise

class ExerciseAdapter() : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private var exerciseList = arrayListOf<Exercise>()

    fun updateList(exercises: List<Exercise>) {
        this.exerciseList.clear()
        this.exerciseList.addAll(exercises)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemBinding = RowExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = exerciseList[position]
        holder.exercise.text = currentItem.name


        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, ExerciseDetailsActivity::class.java).also{
                it.putExtra("Exercise", currentItem)
                holder.itemView.context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class ExerciseViewHolder(private val itemBinding: RowExerciseBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var exercise = itemBinding.tvExercise
    }
}