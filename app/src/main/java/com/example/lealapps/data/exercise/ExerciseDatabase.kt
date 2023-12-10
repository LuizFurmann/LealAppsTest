package com.example.lealapps.data.exercise

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lealapps.model.Exercise

@Database(
    entities = [Exercise::class],
    version = 1,
    exportSchema = true
)

abstract class ExerciseDatabase: RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    companion object{
        @Volatile
        private var INSTANCE: ExerciseDatabase? = null

        fun getDatabase(context: Context): ExerciseDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseDatabase::class.java,
                    "exercise_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}