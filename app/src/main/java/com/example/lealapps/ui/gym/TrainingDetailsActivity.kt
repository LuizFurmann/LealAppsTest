package com.example.lealapps.ui.gym

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lealapps.MainActivity
import com.example.lealapps.R
import com.example.lealapps.databinding.ActivityTrainingDetailsBinding
import com.example.lealapps.model.Exercise
import com.example.lealapps.model.Training
import com.example.lealapps.ui.exercise.ExerciseViewModel
import com.example.lealapps.ui.exerciseDialog.ExerciseDialog
import com.example.lealapps.ui.exerciseDialog.ExerciseListener
import com.example.lealapps.ui.memberDialog.MemberDialog
import com.example.lealapps.ui.memberDialog.MemberListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class TrainingDetailsActivity : AppCompatActivity(), MemberListener, ExerciseListener {

    private lateinit var binding : ActivityTrainingDetailsBinding
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var exerciseViewModel: ExerciseViewModel

    lateinit var training : Training

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        setupToolbar()
        setupViewModel()
        setupEventClicks()

        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
            super.onResume()
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun setupViewModel(){
        if(intent.getSerializableExtra("Training") != null){
            training = intent.getSerializableExtra("Training") as Training
            updateView(training)
            binding.btnSalvaTreino.visibility = View.INVISIBLE
        }else{
            enableFields()
        }
    }

    private fun updateView(training: Training){
        binding.etMember.setText(training.member)
        binding.etExercise.setText(training.exercise)
        binding.etSeries.setText(training.series.toString())
        binding.etRepetition.setText(training.repetition.toString())
    }


    private fun enableFields(){
        binding.etMember.isEnabled = true
        binding.etExercise.isEnabled = true
        binding.etSeries.isEnabled = true
        binding.etRepetition.isEnabled = true

        binding.btnSalvaTreino.visibility = View.VISIBLE
    }

    private fun setupEventClicks(){
        saveExerciseClick()
        memberClick()
        exerciseClick()
    }

    private fun saveExerciseClick(){
        binding.btnSalvaTreino.setOnClickListener {

            val context = this
            val colorState = ColorStateList(
                arrayOf(intArrayOf(- android.R.attr.state_enabled)),
                intArrayOf(ContextCompat.getColor(context, R.color.red))
            )

            val colorStateValid = ColorStateList(
                arrayOf(intArrayOf(- android.R.attr.state_enabled)),
                intArrayOf(ContextCompat.getColor(context, R.color.gray))
            )

            if(binding.etMember.text.isNullOrEmpty()){
                binding.tilMembro.editText?.error = "Campo obrigatório"
                binding.tilMembro.setBoxStrokeColorStateList(colorState)
                binding.tilMembro.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }else{
                binding.tilMembro.editText?.error = null
                binding.tilMembro.setBoxStrokeColorStateList(colorStateValid)
                binding.tilMembro.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            }

            if(binding.etExercise.text.isNullOrEmpty()){
                binding.tilExercise.editText?.error = "Campo obrigatório"
                binding.tilExercise.setBoxStrokeColorStateList(colorState)
                binding.tilExercise.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }else{
                binding.tilMembro.editText?.error = null
                binding.tilMembro.setBoxStrokeColorStateList(colorStateValid)
                binding.tilMembro.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            }

            if(binding.etSeries.text.isNullOrEmpty()){
                binding.tilSeries.editText?.error = "Campo obrigatório"
                binding.tilSeries.setBoxStrokeColorStateList(colorState)
                binding.tilSeries.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }else{
                binding.tilMembro.editText?.error = null
                binding.tilMembro.setBoxStrokeColorStateList(colorStateValid)
                binding.tilMembro.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            }

            if(binding.etRepetition.text.isNullOrEmpty()){
                binding.tilRepetition.editText?.error = "Campo obrigatório"
                binding.tilRepetition.setBoxStrokeColorStateList(colorState)
                binding.tilRepetition.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            }else{
                binding.tilMembro.editText?.error = null
                binding.tilMembro.setBoxStrokeColorStateList(colorStateValid)
                binding.tilMembro.hintTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            }

            if (validateSaveExercise()) {
                if(intent.getSerializableExtra("Training") != null){
                    updateExercise()
                    finish()
                }else{
                    insertDataToDatabase()
                    clearFields()
                }
            }
        }
    }

    fun validateSaveExercise(): Boolean{
        if(binding.tilMembro.editText?.text.toString().isEmpty()){
            return false
        }else if(binding.tilExercise.editText?.text.toString().isEmpty()){
            return false
        }else if(binding.tilSeries.editText?.text.toString().isEmpty()){
            return false
        }else if(binding.tilRepetition.editText?.text.toString().isEmpty()){
            return false
        }
        return true
    }

    private fun clearFields(){
        binding.etMember.setText("")
        binding.etExercise.setText("")
        binding.etSeries.setText("")
        binding.etRepetition.setText("")
    }

    private fun deleteTrainingConfirmation(){
            val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
            builder.setMessage("Deseja deletar o treino?")
            builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                deleteTraining()
            }
            builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
            }
            builder.show()
    }

    private fun insertDataToDatabase() {
        var day = intent.getStringExtra("day").toString()
        val series = binding.etSeries.text
        val repetition = binding.etRepetition.text

        // Create User Object
        val training = Training(0, day, binding.etMember.text.toString(),binding.etExercise.text.toString(), Integer.parseInt(series.toString()), Integer.parseInt(repetition.toString()))

        // Add Data to database
        trainingViewModel.addTraining(training)
        Toast.makeText(this, "Treino adicionado", Toast.LENGTH_LONG).show()

    }
    private fun updateExercise(){
        training = intent.getSerializableExtra("Training") as Training
        var day = training.day
        var id = training.id
        val series = binding.etSeries.text
        val repetition = binding.etRepetition.text

        val updatedTraining = Training(id, day, binding.etMember.text.toString(),binding.etExercise.text.toString(), Integer.parseInt(series.toString()), Integer.parseInt(repetition.toString()))
        // Update Current User
        trainingViewModel.updateTraining(updatedTraining)
        Toast.makeText(this, "Treino editado", Toast.LENGTH_LONG).show()
    }

    private fun deleteTraining(){
        training = intent.getSerializableExtra("Training") as Training
        var day = intent.getStringExtra("day").toString()
        var id = training.id
        val series = binding.etSeries.text
        val repetition = binding.etRepetition.text

        val updatedTraining = Training(id, day, binding.etMember.text.toString(),binding.etExercise.text.toString(), Integer.parseInt(series.toString()), Integer.parseInt(repetition.toString()))
        // Update Current User
        trainingViewModel.deleteTraining(updatedTraining)
        Toast.makeText(this, "Deletado com sucesso!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun memberClick(){
        binding.tilMembro.editText?.setOnClickListener {
            val dialog = MemberDialog(this, this)
            dialog.show()
        }
    }

    private fun exerciseClick(){
        binding.tilExercise.editText?.setOnClickListener {
            if (!binding.etMember.text.isNullOrEmpty()) {
                exerciseViewModel.readAllData()
                    .observe(this) { exercises: List<Exercise> -> choseExercise(exercises) }
            }else {
                val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
                builder.setMessage(getString(R.string.member_missing_alert))
                builder.setPositiveButton(getString(R.string.understand)) { dialog, which -> }
                builder.show()
            }
        }
    }

    private fun choseExercise(exercises: List<Exercise>){
        val dialog = ExerciseDialog(this, this, exercises)
        dialog.show()
    }

    override fun selectedMember(value: String) {
        binding.etMember.setText(value)
    }

    override fun selectedExercise(value: String) {
        binding.etExercise.setText(value)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(intent.getSerializableExtra("Training") != null){
            menuInflater.inflate(R.menu.menu_item, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.edit){
            enableFields()
            return true
        }else if(item.itemId == R.id.delete){
            deleteTrainingConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun setupToolbar(){
//        binding.toolbarExerciseDetais.title = ""
//        setSupportActionBar(binding.toolbarExerciseDetais)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }
}