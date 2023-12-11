package com.example.lealappstest.view.exercise

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityExerciseDetailsBinding
import com.example.lealappstest.model.Exercise
import com.example.lealappstest.model.Training
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File


class ExerciseDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseDetailsBinding
    private lateinit var exerciseViewModel: ExerciseViewModel

    private val REQUEST_IMAGE = 100
    private var profilePicturePath: String? = null

    lateinit var exercise: Exercise
    lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        saveExercise()
        selectImage()
    }

    private fun selectImage(){
        binding.selectedImage.setOnClickListener {
            imageChooser()
        }
    }

    private fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_OPEN_DOCUMENT

        startActivityForResult(Intent.createChooser(i, getString(R.string.selectImage)), REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_IMAGE) {
                val selectedImageUri = data?.data
                if (null != selectedImageUri) {

                    val rPermPersist = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    this.contentResolver.takePersistableUriPermission(data?.data!!, rPermPersist)
                    binding.selectedImage.setImageURI(data?.data)

                    profilePicturePath = selectedImageUri.toString()
                }
            }
    }
    private fun setupViewModel() {
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
            if (intent.getSerializableExtra("Exercise") != null) {
                exercise = intent.getSerializableExtra("Exercise") as Exercise
                updateView(exercise)
                setTitle("Exercício ${exercise.name.toString()}")
                binding.btnSaveExercise.visibility = View.GONE
                binding.selectImageTittle.visibility = View.GONE
            } else {
                binding.etName.isEnabled = true
                binding.etDescription.isEnabled = true
            }
    }

    private fun updateView(exercise: Exercise) {
         var myUri : Uri = Uri.parse(exercise.image);
        profilePicturePath = myUri.toString()

        binding.etName.setText(exercise.name)
        binding.selectedImage.setImageURI(myUri)
        binding.etDescription.setText(exercise.observation)
    }

    private fun saveExercise() {
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
                checkExerciseExists()
            }
        }
    }

    private fun validateExercise(): Boolean {
        if (profilePicturePath.isNullOrEmpty()) {
            return false
        }
        if (binding.tilName.editText?.text.toString().isEmpty()) {
            return false
        }
        if (binding.tilDescription.editText?.text.toString().isEmpty()) {
            return false
        }
        return true
    }

    private fun insertDataToDatabase() {
        if (intent.getSerializableExtra("Training") != null) {
            training = intent.getSerializableExtra("Training") as Training
            val exercise = Exercise(
                0, training.name.toString().toInt(), binding.etName.text.toString(),
                profilePicturePath.toString(), binding.etDescription.text.toString()
            )
            exerciseViewModel.addExercise(exercise)
            Toast.makeText(this, getString(R.string.exerciseAdd), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, exercise.training.toString().toInt(), binding.etName.text.toString(), profilePicturePath.toString(), binding.etDescription.text.toString())
        exerciseViewModel.updateExercise(updatedExercise)
        Toast.makeText(this, getString(R.string.exerciseEdited), Toast.LENGTH_LONG).show()
    }

    private fun deleteExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, exercise.training.toString().toInt(), binding.etName.text.toString(), profilePicturePath.toString(), binding.etDescription.text.toString())
        // Update Current User
        exerciseViewModel.deleteExercise(updatedExercise)
        Toast.makeText(this, getString(R.string.deletedSuccess), Toast.LENGTH_LONG).show()
        finish()
    }

    private fun deleteExerciseConfirmation() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage(getString(R.string.deleteExerciseConfirmation))
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            deleteExercise()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

    private fun checkExerciseExists(){
        var exists = false
        if (intent.getSerializableExtra("Training") != null) {
            training = intent.getSerializableExtra("Training") as Training
            exerciseViewModel.readAllData(training.name.toString().toInt()).observe(this) { trainings ->
                if(trainings.isNotEmpty()){
                    trainings.size
                    trainings?.forEach lit@{
                        if(it.name == binding.etName.text.toString()){
                            exists = true
                            return@lit
                        }
                    }
                }

                if(exists){
                    exerciseExists()
                }else{
                    if (intent.getSerializableExtra("Exercise") != null) {
                        updateExercise()
                        finish()
                    } else {
                        insertDataToDatabase()
                        finish()
                    }
                }
            }
        }else{
            exerciseViewModel.readAllData(exercise.training).observe(this) { exercises ->
                if(exercises.isNotEmpty()){
                    exercises.size
                    exercises?.forEach lit@{
                        if(it.name == binding.etName.text.toString()){
                            exists = true
                            return@lit
                        }
                    }
                }

                if(exists){
                    exerciseExists()
                }else{
                    if (intent.getSerializableExtra("Exercise") != null) {
                        updateExercise()
                        finish()
                    } else {
                        insertDataToDatabase()
                        finish()
                    }
                }
            }
        }
    }

    private fun exerciseExists() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage(getString(R.string.exerciseExistsAlert))
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            if (intent.getSerializableExtra("Exercise") != null) {
                updateExercise()
                finish()
            } else {
                insertDataToDatabase()
                finish()
            }
        }
        builder.setNegativeButton(getString(R.string.no)){dialog, which ->
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (intent.getSerializableExtra("Exercise") != null) {
            menuInflater.inflate(R.menu.menu_item, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit) {
            binding.etName.isEnabled = true
            binding.etDescription.isEnabled = true
            binding.btnSaveExercise.visibility = View.VISIBLE
            binding.selectImageTittle.visibility = View.VISIBLE
            return true
        } else if (item.itemId == R.id.delete) {
            deleteExerciseConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}