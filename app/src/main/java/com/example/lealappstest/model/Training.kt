package com.example.lealappstest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "training_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: Int,
    val description: String,
    val date: String,
): Serializable
