package com.example.lealappstest.view.exercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lealappstest.data.exercise.ExerciseDatabase
import com.example.lealappstest.data.training.TrainingDatabase
import com.example.lealappstest.model.Exercise
import com.example.lealappstest.model.Training
import com.example.lealappstest.repository.ExerciseRepository
import com.example.lealappstest.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application): AndroidViewModel(application) {

    private val exerciseRepository : ExerciseRepository

    init {
        val exerciseDao = ExerciseDatabase.getDatabase(application).exerciseDao()
        exerciseRepository = ExerciseRepository(exerciseDao)
    }

    fun readAllData(): LiveData<List<Exercise>> = exerciseRepository.readAllData()

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.addExercise(exercise)
        }
    }
    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            exerciseRepository.updateExercise(exercise)
        }
    }
    fun deleteExercise(exercise: Exercise) {
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