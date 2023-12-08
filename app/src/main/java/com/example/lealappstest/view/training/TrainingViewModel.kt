package com.example.lealappstest.view.training

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lealappstest.data.training.TrainingDatabase
import com.example.lealappstest.model.Training
import com.example.lealappstest.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingViewModel(application: Application): AndroidViewModel(application) {

    private val trainingRepository : TrainingRepository

    init {
        val exerciseDao = TrainingDatabase.getDatabase(application).exerciseDao()
        trainingRepository = TrainingRepository(exerciseDao)
    }

    fun readAllData(): LiveData<List<Training>> = trainingRepository.readAllData()

    fun addTraining(exercise: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingRepository.addTraining(exercise)
        }
    }
    fun updateTraining(exercise: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingRepository.updateTraining(exercise)
        }
    }
    fun deleteTraining(exercise: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingRepository.deleteTraining(exercise)
        }
    }
    fun deleteExerciseDb() {
        viewModelScope.launch(Dispatchers.IO) {
            trainingRepository.deleteTrainingDb()
        }
    }
}