package com.example.lealapps.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "training_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val day: String,
    val member: String,
    val exercise : String,
    val series: Int,
    val repetition: Int
): Serializable
