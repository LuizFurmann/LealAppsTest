package com.example.lealappstest.repository

import androidx.lifecycle.LiveData
import com.example.lealappstest.data.training.TrainingDao
import com.example.lealappstest.model.Training

class TrainingRepository(private val trainingDao: TrainingDao) {
    fun readAllData() : LiveData<List<Training>> = trainingDao.readALlData()

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