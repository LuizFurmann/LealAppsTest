package com.example.lealappstest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val image: String,
    val observation: String
): Serializable