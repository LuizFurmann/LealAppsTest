package com.example.lealappstest.view.training

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lealappstest.databinding.RowTrainingBinding
import com.example.lealappstest.model.Training
import com.example.lealappstest.view.exercise.ExerciseActivity

class TrainingAdapter() : RecyclerView.Adapter<TrainingAdapter.ExerciseViewHolder>() {

    private var trainingList = arrayListOf<Training>()

    fun updateList(trainings: List<Training>) {
        this.trainingList.clear()
        this.trainingList.addAll(trainings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemBinding = RowTrainingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem = trainingList[position]
        holder.exercise.text = currentItem.name.toString()

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, ExerciseActivity::class.java).also{
                it.putExtra("Training", currentItem)
                holder.itemView.context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    class ExerciseViewHolder(private val itemBinding: RowTrainingBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var exercise = itemBinding.tvTrainingNumber
    }
}