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
    lateinit var exerciseViewModel: ExerciseViewModel

    val REQUEST_IMAGE = 100
    var profilePicturePath: String? = null

    lateinit var exercise: Exercise
    lateinit var training: Training

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        saveExercise()
        selectImage()

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    fun selectImage(){
        binding.selectedImage.setOnClickListener {
            imageChooser()
        }
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_OPEN_DOCUMENT

        startActivityForResult(Intent.createChooser(i, "Select Picture"), REQUEST_IMAGE)
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
    fun setupViewModel() {
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
            if (intent.getSerializableExtra("Exercise") != null) {
                exercise = intent.getSerializableExtra("Exercise") as Exercise
                updateView(exercise)
                setTitle("Exercício ${exercise.name.toString()}")
                binding.btnSaveExercise.visibility = View.INVISIBLE
            } else {
                binding.etName.isEnabled = true
                binding.etDescription.isEnabled = true
            }
    }

    private fun updateView(exercise: Exercise) {
         var myUri : Uri = Uri.parse(exercise.image);

        binding.etName.setText(exercise.name)
        binding.selectedImage.setImageURI(myUri)
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
                    ContextCompat.getColor(context, R.color.grey)
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
                if (intent.getSerializableExtra("Exercise") != null) {
                    updateExercise()
                    finish()
                } else {
                    insertDataToDatabase()
                    binding.etName.setText("")
                    binding.etDescription.setText("")
                }
            }
        }
    }

    fun validadeExercise(): Boolean {
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
            Toast.makeText(this, "Exercício adicionado", Toast.LENGTH_LONG).show()
        }

    }

    private fun updateExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, exercise.training.toString().toInt(), binding.etName.text.toString(), profilePicturePath.toString(), binding.etDescription.text.toString())
        exerciseViewModel.updateExercise(updatedExercise)
        Toast.makeText(this, "Treino editado", Toast.LENGTH_LONG).show()
    }

    private fun deleteExercise(){
        exercise = intent.getSerializableExtra("Exercise") as Exercise
        var id = exercise.id

        val updatedExercise = Exercise(id, exercise.training.toString().toInt(), binding.etName.text.toString(), profilePicturePath.toString(), binding.etDescription.text.toString())
        // Update Current User
        exerciseViewModel.deleteExercise(updatedExercise)
        Toast.makeText(this, "Deletado com sucesso!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun deleteExerciseConfirmation() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar o exercício?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            deleteExercise()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
//            val imageUri = data?.data
//            ActivityResultContracts.GetContent()
//
//            profilePicturePath = imageUri.toString()
//            binding.selectedImage.setImageURI(data?.data)
//        }
//    }

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
            return true
        } else if (item.itemId == R.id.delete) {
            deleteExerciseConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}