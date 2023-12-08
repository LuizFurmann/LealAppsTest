package com.example.lealappstest.data.training

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lealappstest.model.Training

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTraining(training: Training)

    @Update
    suspend fun updateTraining(training: Training)

    @Delete
    suspend fun deleteTraining(training: Training)

    @Query("DELETE FROM training_table")
    suspend fun deleteTrainingDb()

    @Query("SELECT * FROM training_table ORDER BY name ASC")
    fun readALlData(): LiveData<List<Training>>
}