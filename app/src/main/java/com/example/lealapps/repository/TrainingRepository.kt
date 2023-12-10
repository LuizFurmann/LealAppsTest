package com.example.lealapps.repository

import androidx.lifecycle.LiveData
import com.example.lealapps.data.training.TrainingDao
import com.example.lealapps.model.Training

class TrainingRepository(private val trainingDao: TrainingDao) {
    fun readAllData(diaEscolhido: String) : LiveData<List<Training>> = trainingDao.readALlData(diaEscolhido)

    suspend fun addTraining(training: Training){
        trainingDao.addTraining(training)
    }
    suspend fun updateTraining(training: Training){
        trainingDao.updateTraining(training)
    }
    suspend fun deleteTraining(training: Training){
        trainingDao.deleteTraining(training)
    }
    suspend fun deleteTrainingDb(){
        trainingDao.deleteTrainingDb()
    }
}