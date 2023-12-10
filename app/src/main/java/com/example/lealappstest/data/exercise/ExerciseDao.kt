package com.example.lealappstest.data.exercise

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lealappstest.model.Exercise

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

    @Query("SELECT * FROM exercise_table WHERE training =:training ORDER BY name ASC")
    fun readALlData(training : Int): LiveData<List<Exercise>>
}