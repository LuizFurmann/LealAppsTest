package com.example.lealapps.ui.exercise

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lealapps.R
import com.example.lealapps.databinding.ActivityExerciseDetailsBinding
import com.example.lealapps.model.Exercise
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ExerciseDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseDetailsBinding
    lateinit var exerciseViewModel: ExerciseViewModel

    lateinit var exercise: Exercise

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        saveExercise()
    }


    fun setupViewModel(){
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        if(intent.getSerializableExtra("Exercise") != null){
            exercise = intent.getSerializableExtra("Exercise") as Exercise
            updateView(exercise)
            binding.btnSaveExercise.visibility = View.INVISIBLE
        }else{
            binding.etName.isEnabled = true
            binding.etImage.isEnabled = true
            binding.etDescription.isEnabled = true
        }
    }

    private fun updateView(exercise: Exercise){
        binding.etName.setText(exercise.name)
        binding.etImage.setText(exercise.image)
        binding.etDescription.setText(exercise.observation)
    }

    fun saveExercise() {
        binding.btnSaveExercise.setOnClickListener {
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
                    ContextCompat.getColor(context, R.color.gray)
                )
            )

            if (binding.tilName.editText?.text.toString().isNullOrEmpty()) {
                binding.tilName.editText?.error = "Campo obrigatório"
                binding.tilName.setBoxStrokeColorStateList(colorState)
                binding.tilName.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilName.editText?.error = null
                binding.tilName.setBoxStrokeColorStateList(colorStateValid)
                binding.tilName.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray))
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
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray))
            }

            if (validadeExercise()) {
                if(intent.getSerializableExtra("Exercise") != null){
                    updateExercise()
                    finish()
                }else{
                    insertDataToDatabase()
                    binding.etName.setText("")
                    binding.etImage.setText("")
                    binding.etDescription.setText("")
                }
            }
        }
    }
    fun validadeExercise(): Boolean {
        if(binding.tilName.editText?.text.toString().isEmpty()){
            return false
        }
        if(binding.tilDescription.editText?.text.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun insertDataToDatabase() {
        val exercise = Exercise(0, binding.etName.text.toString(), binding.etImage.text.toString(), binding.etDescription.text.toString())
        exerciseViewModel.addTraining(exercise)
        Toast.makeText(this, "Exercício adicionado", Toast.LENGTH_LONG).show()
    }

    private fun updateExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, binding.etName.text.toString(), binding.etImage.text.toString(), binding.etDescription.text.toString())
        exerciseViewModel.updateTraining(updatedExercise)
        Toast.makeText(this, "Treino editado", Toast.LENGTH_LONG).show()
    }

    private fun deleteExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, binding.etName.text.toString(), binding.etImage.text.toString(), binding.etDescription.text.toString())
        // Update Current User
        exerciseViewModel.deleteTraining(updatedExercise)
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
        if(intent.getSerializableExtra("Exercise") != null){
            menuInflater.inflate(R.menu.menu_item, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.edit){
            binding.etName.isEnabled = true
            binding.etImage.isEnabled = true
            binding.etDescription.isEnabled = true
            binding.btnSaveExercise.visibility = View.VISIBLE
            return true
        }else if(item.itemId == R.id.delete){
            deleteExerciseConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}