package com.example.lealapps.ui.gym

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lealapps.data.training.TrainingDatabase
import com.example.lealapps.model.Training
import com.example.lealapps.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrainingViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TrainingRepository
    init {
        val exerciseDao = TrainingDatabase.getDatabase(application).exerciseDao()
        repository= TrainingRepository(exerciseDao)
    }

    fun readAllData(diaEscolhido: String): LiveData<List<Training>> = repository.readAllData(diaEscolhido)

    fun addTraining(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTraining(training)
        }
    }
    fun updateTraining(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTraining(training)
        }
    }
    fun deleteTraining(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTraining(training)
        }
    }
    fun deleteTrainingDb() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTrainingDb()
        }
    }
}