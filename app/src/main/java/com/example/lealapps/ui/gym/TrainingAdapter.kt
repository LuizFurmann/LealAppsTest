package com.example.lealapps.ui.gym

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lealapps.R
import com.example.lealapps.model.Training

class TrainingAdapter(): RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder>(){

    private var trainingList = emptyList<Training>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        return TrainingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_textview_treino, parent, false))
    }

    override fun getItemCount(): Int = trainingList.size

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        val currentItem = trainingList[position]

        holder.itemView.findViewById<TextView>(R.id.tilMember).text = currentItem.member
        holder.itemView.findViewById<TextView>(R.id.nomeTreino).text = currentItem.exercise
        holder.itemView.findViewById<TextView>(R.id.series).text = currentItem.series.toString()
        holder.itemView.findViewById<TextView>(R.id.repetition).text = currentItem.repetition.toString()
        holder.itemView.findViewById<View>(R.id.viewBoard)

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, TrainingDetailsActivity::class.java).also{
                it.putExtra("Training", currentItem)
                holder.itemView.context.startActivity(it)
            }
        }
    }

    class TrainingViewHolder (itemView : View): RecyclerView.ViewHolder(itemView)

    fun setData(training: List<Training>) {
        this.trainingList = training
        notifyDataSetChanged()
    }
}