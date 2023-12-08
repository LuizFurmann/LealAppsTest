package com.example.lealappstest.view.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityMainBinding
import com.example.lealappstest.model.Training

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var trainingViewModel: TrainingViewModel
    private val trainingAdapter = TrainingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        newExercise()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvTraining.layoutManager = layoutManager;
        binding.rvTraining.adapter = trainingAdapter
        binding.rvTraining.setHasFixedSize(true)
    }

    private fun setupViewModel(){
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        trainingViewModel.readAllData().observe(this) {
                trainings -> updateList(trainings)
        }
    }

    private fun updateList(trainings: List<Training>){
        if (trainings.isEmpty()) {
            binding.rvTraining.visibility = View.GONE
//            binding.tvNoListOrder.visibility = View.VISIBLE
//            binding.imgNoListOrder.visibility = View.VISIBLE
        } else {
            binding.rvTraining.visibility = View.VISIBLE
//            binding.tvNoListOrder.visibility = View.GONE
//            binding.imgNoListOrder.visibility = View.GONE
            trainingAdapter.updateList(trainings)
        }
    }

    private fun newExercise() {
        binding.fabNewTraining.setOnClickListener {

            Intent(this@MainActivity, TrainingDetailsActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}