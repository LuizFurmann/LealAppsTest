package com.example.lealappstest.view.training

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityTrainingDetailsBinding
import com.example.lealappstest.model.Exercise
import com.example.lealappstest.model.Training
import com.example.lealappstest.view.exercise.ExerciseViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.sql.Timestamp
import java.util.Calendar

class TrainingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingDetailsBinding

    private lateinit var trainingViewModel: TrainingViewModel
    private lateinit var exerciseViewModel: ExerciseViewModel

    lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTrainingDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        saveExercise()
        getPickDate()
    }

    fun setupViewModel() {
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        if (intent.getSerializableExtra("Training") != null) {
            training = intent.getSerializableExtra("Training") as Training
            updateView(training)
        } else {
            binding.etName.isEnabled = true
            binding.etDescription.isEnabled = true
            binding.etDate.isEnabled = true
        }
    }

    private fun updateView(training: Training) {
        binding.etName.setText(training.name.toString())
        binding.etDescription.setText(training.description)
        binding.etDate.setText(training.date.toString())
    }

    private fun saveExercise() {
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

            if (binding.tilName.editText?.text.toString().isNullOrEmpty()) {
                binding.tilName.editText?.error = getString(R.string.requiredField)
                binding.tilName.setBoxStrokeColorStateList(colorState)
                binding.tilName.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilName.editText?.error = null
                binding.tilName.setBoxStrokeColorStateList(colorStateValid)
                binding.tilName.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (binding.tilDescription.editText?.text.toString().isNullOrEmpty()) {
                binding.tilDescription.editText?.error = getString(R.string.requiredField)
                binding.tilDescription.setBoxStrokeColorStateList(colorState)
                binding.tilDescription.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilDescription.editText?.error = null
                binding.tilDescription.setBoxStrokeColorStateList(colorStateValid)
                binding.tilDescription.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (validateExercise()) {
                checkTrainingExists()
            }
        }
    }

    private fun validateExercise(): Boolean {
        if (binding.tilName.editText?.text.toString().isEmpty()) {
            return false
        }
        if (binding.tilDescription.editText?.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    private fun insertDataToDatabase() {
        val training = Training(
            0,
            binding.etName.text.toString().toInt(),
            binding.etDescription.text.toString(),
            binding.etDate.text.toString()
        )
        trainingViewModel.addTraining(training)
        Toast.makeText(this, getString(R.string.trainingAdd), Toast.LENGTH_LONG).show()
    }

    private fun updateTraining() {
        training = intent.getSerializableExtra("Training") as Training

        var id = training.id

        exerciseViewModel.readAllData(training.name.toString().toInt()).observe(this) { exercises ->
            exercises?.forEach {
                val updatedExercise = Exercise(
                    it.id,
                    binding.etName.text.toString().toInt(),
                    it.name,
                    it.image,
                    it.observation
                )
                exerciseViewModel.updateExercise(updatedExercise)
            }
        }

        val updatedTraining = Training(
            id,
            binding.etName.text.toString().toInt(),
            binding.etDescription.text.toString(),
            binding.etDate.text.toString()
        )
        trainingViewModel.updateTraining(updatedTraining)
        finish()

        Toast.makeText(this, getString(R.string.trainingEdited), Toast.LENGTH_LONG).show()
    }

    private fun checkTrainingExists() {
        var exists = false
        trainingViewModel.readAllData().observe(this) { trainings ->
            if (trainings.isNotEmpty()) {
                trainings.size
                trainings?.forEach lit@{
                    if (it.name == binding.etName.text.toString().toInt()) {
                        exists = true
                        return@lit
                    }
                }
            }

            if (exists) {
                trainingExists()
            } else {
                if (intent.getSerializableExtra("Training") != null) {
                    updateTraining()
                    finish()
                } else {
                    insertDataToDatabase()
                    finish()
                }
            }
        }
    }

    private fun trainingExists() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage(getString(R.string.trainingExistsAlert))
        builder.setPositiveButton(getString(R.string.understand)) { dialog, which ->
        }
        builder.show()
    }

    private fun getPickDate() {
        binding.tilDate.editText?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                    binding.etDate.setText(dat)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
}