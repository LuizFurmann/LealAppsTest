package com.example.lealapps.data.exercise

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lealapps.model.Exercise

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExercise(training: Exercise)

    @Update
    suspend fun updateExercise(training: Exercise)

    @Delete
    suspend fun deleteExercise(training: Exercise)

    @Query("DELETE FROM exercise_table")
    suspend fun deleteExerciseDb()

    @Query("SELECT * FROM exercise_table ORDER BY name ASC")
    fun readALlData(): LiveData<List<Exercise>>
}