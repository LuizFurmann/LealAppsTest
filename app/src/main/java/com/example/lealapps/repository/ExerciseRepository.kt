package com.example.lealapps.repository

import androidx.lifecycle.LiveData
import com.example.lealapps.data.exercise.ExerciseDao
import com.example.lealapps.model.Exercise

class ExerciseRepository (private val exerciseDao: ExerciseDao){

    fun readAllData(): LiveData<List<Exercise>> = exerciseDao.readALlData()

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