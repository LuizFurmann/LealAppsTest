package com.example.lealappstest.repository

import androidx.lifecycle.LiveData
import com.example.lealappstest.data.exercise.ExerciseDao
import com.example.lealappstest.model.Exercise

class ExerciseRepository (private val exerciseDao: ExerciseDao){

    fun readAllData(training : Int): LiveData<List<Exercise>> = exerciseDao.readALlData(training)

    suspend fun addExercise(exercise: Exercise){
        exerciseDao.addExercise(exercise)
    }

    suspend fun updateExercise(exercise: Exercise){
        exerciseDao.updateExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise){
        exerciseDao.deleteExercise(exercise)
    }

    suspend fun deleteExerciseDb(){
        exerciseDao.deleteExerciseDb()
    }
}