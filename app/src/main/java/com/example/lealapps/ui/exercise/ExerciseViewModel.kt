package com.example.lealapps.ui.exercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lealapps.data.exercise.ExerciseDatabase
import com.example.lealapps.model.Exercise
import com.example.lealapps.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application): AndroidViewModel(application) {

    private val exerciseRepository : ExerciseRepository

    init {
        val exerciseDao = ExerciseDatabase.getDatabase(application).exerciseDao()
        exerciseRepository= ExerciseRepository(exerciseDao)
    }

    fun readAllData(): LiveData<List<Exercise>> = exerciseRepository.readAllData()

    fun addTraining(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.addExercise(exercise)
        }
    }
    fun updateTraining(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.updateExercise(exercise)
        }
    }
    fun deleteTraining(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.deleteExercise(exercise)
        }
    }
    fun deleteExerciseDb() {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.deleteExerciseDb()
        }
    }
}