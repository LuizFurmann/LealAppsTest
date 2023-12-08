package com.example.lealappstest.data.training

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lealappstest.model.Training

@Database(
    entities = [Training::class],
    version = 1,
    exportSchema = true
)

abstract class TrainingDatabase: RoomDatabase() {
    abstract fun exerciseDao(): TrainingDao

    companion object{
        @Volatile
        private var INSTANCE: TrainingDatabase? = null

        fun getDatabase(context: Context): TrainingDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrainingDatabase::class.java,
                    "training_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}