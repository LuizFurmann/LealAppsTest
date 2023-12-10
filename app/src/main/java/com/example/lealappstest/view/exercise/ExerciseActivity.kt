package com.example.lealappstest.view.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityExerciseBinding
import com.example.lealappstest.model.Exercise
import com.example.lealappstest.model.Training
import com.example.lealappstest.view.training.TrainingDetailsActivity
import com.example.lealappstest.view.training.TrainingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExerciseBinding

    lateinit var exerciseViewModel: ExerciseViewModel
    lateinit var trainingViewModel: TrainingViewModel
    private val exerciseAdapter = ExerciseAdapter()

    lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateTrainingDetails()
        setTitle("Treino ${training.name.toString()}")
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
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        exerciseViewModel.readAllData(training.name.toString().toInt()).observe(this) {
                exercises -> updateList(exercises)
        }
    }

    fun updateTrainingDetails(){
        training = intent.getSerializableExtra("Training") as Training

        binding.trainingNumberValue.text = training.name
        binding.trainingDateValue.text = training.date
        binding.trainingObservationValue.text = training.description
    }

    private fun updateList(exercises: List<Exercise>){
        if (exercises.isEmpty()) {
            binding.rvExercise.visibility = View.GONE
            binding.myExercisesTittle.visibility = View.GONE
            binding.emptyExerciseList.visibility = View.VISIBLE
        } else {
            binding.rvExercise.visibility = View.VISIBLE
            binding.myExercisesTittle.visibility = View.VISIBLE
            binding.emptyExerciseList.visibility = View.GONE
            exerciseAdapter.updateList(exercises)
        }
    }

    private fun newExercise() {
        binding.fabNewExercise.setOnClickListener {
            Intent(this@ExerciseActivity, ExerciseDetailsActivity::class.java).also{
                it.putExtra("Training", training)
                startActivity(it)
            }
        }
    }

    private fun deleteExercise(){
        training = intent.getSerializableExtra("Training") as Training
        var id = training.id

        val updatedExercise = Training(id, training.name, training.description, training.date)
        // Update Current User
        trainingViewModel.deleteTraining(updatedExercise)
        Toast.makeText(this, "Deletado com sucesso!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun deleteExerciseConfirmation(){
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar o exercício?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            deleteExercise()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(intent.getSerializableExtra("Training") != null){
            menuInflater.inflate(R.menu.menu_item, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        training = intent.getSerializableExtra("Training") as Training
        if(item.itemId == R.id.edit){
            Intent(this@ExerciseActivity, TrainingDetailsActivity::class.java).also{
                it.putExtra("Training", training)
                startActivity(it)
                finish()
            }
            return true
        }else if(item.itemId == R.id.delete){
            deleteExerciseConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}