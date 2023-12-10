package com.example.lealappstest.view.training

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityTrainingDetailsBinding
import com.example.lealappstest.model.Training
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TrainingDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTrainingDetailsBinding

    lateinit var trainingViewModel: TrainingViewModel

    lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTrainingDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        saveExercise()
    }


    fun setupViewModel(){
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]

        if(intent.getSerializableExtra("Training") != null){
            training = intent.getSerializableExtra("Training") as Training
            updateView(training)
        }else{
            binding.etNumber.isEnabled = true
            binding.etDescription.isEnabled = true
            binding.etDate.isEnabled = true
        }
    }

    private fun updateView(training: Training){
        binding.etNumber.setText(training.name)
        binding.etDescription.setText(training.date)
        binding.etDate.setText(training.description)
    }

    fun saveExercise() {
        binding.btnSaveTraining.setOnClickListener {
            val context = this
            val colorState = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.red)
                )
            )

            val colorStateValid = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.grey)
                )
            )

            if (binding.tilNumber.editText?.text.toString().isNullOrEmpty()) {
                binding.tilNumber.editText?.error = "Campo obrigatório"
                binding.tilNumber.setBoxStrokeColorStateList(colorState)
                binding.tilNumber.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilNumber.editText?.error = null
                binding.tilNumber.setBoxStrokeColorStateList(colorStateValid)
                binding.tilNumber.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (binding.tilDescription.editText?.text.toString().isNullOrEmpty()) {
                binding.tilDescription.editText?.error = "Campo obrigatório"
                binding.tilDescription.setBoxStrokeColorStateList(colorState)
                binding.tilDescription.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilDescription.editText?.error = null
                binding.tilDescription.setBoxStrokeColorStateList(colorStateValid)
                binding.tilDescription.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (validadeExercise()) {
                if(intent.getSerializableExtra("Training") != null){
                    updateExercise()
                    finish()
                }else{
                    insertDataToDatabase()
                    binding.etNumber.setText("")
                    binding.etDescription.setText("")
                    binding.etDate.setText("")
                }
            }
        }
    }
    fun validadeExercise(): Boolean {
        if(binding.tilNumber.editText?.text.toString().isEmpty()){
            return false
        }
        if(binding.tilDescription.editText?.text.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun insertDataToDatabase() {
        val training = Training(0, binding.etNumber.text.toString(), binding.etDescription.text.toString(), binding.etDate.text.toString())
        trainingViewModel.addTraining(training)
        Toast.makeText(this, "Exercício adicionado", Toast.LENGTH_LONG).show()
    }

    private fun updateExercise(){
        training = intent.getSerializableExtra("Training") as Training
        var id = training.id

        val updatedExercise = Training(id, binding.etNumber.text.toString(), binding.etDescription.text.toString(), binding.etDate.text.toString())
        trainingViewModel.updateTraining(updatedExercise)
        Toast.makeText(this, "Treino editado", Toast.LENGTH_LONG).show()
    }

    private fun deleteExercise(){
        training = intent.getSerializableExtra("Training") as Training
        var id = training.id

        val updatedExercise = Training(id, binding.etNumber.text.toString(), binding.etDescription.text.toString(), binding.etDate.text.toString())
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
}