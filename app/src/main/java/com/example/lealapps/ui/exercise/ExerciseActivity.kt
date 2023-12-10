package com.example.lealapps.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lealapps.databinding.ActivityExerciseBinding
import com.example.lealapps.model.Exercise


class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    lateinit var exerciseViewModel: ExerciseViewModel
    private val exerciseAdapter = ExerciseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        newExercise()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvExercise.layoutManager = layoutManager;
        binding.rvExercise.adapter = exerciseAdapter
        binding.rvExercise.setHasFixedSize(true)
    }

    private fun setupViewModel(){
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        exerciseViewModel.readAllData().observe(this) {
                exercises -> updateList(exercises)
        }
    }

    private fun updateList(exercises: List<Exercise>){
        if (exercises.isEmpty()) {
            binding.rvExercise.visibility = View.GONE
//            binding.tvNoListOrder.visibility = View.VISIBLE
//            binding.imgNoListOrder.visibility = View.VISIBLE
        } else {
            binding.rvExercise.visibility = View.VISIBLE
//            binding.tvNoListOrder.visibility = View.GONE
//            binding.imgNoListOrder.visibility = View.GONE
            exerciseAdapter.updateList(exercises)
        }
    }

    private fun newExercise() {
        binding.fabNewExercise.setOnClickListener {

            Intent(this@ExerciseActivity, ExerciseDetailsActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}