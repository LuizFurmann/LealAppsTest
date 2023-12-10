package com.example.lealapps.data.training

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lealapps.model.Training

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

    @Query("SELECT * FROM training_table WHERE day =:diaEscolhido ORDER BY exercise ASC")
    fun readALlData(diaEscolhido: String): LiveData<List<Training>>
}