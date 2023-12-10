package com.example.lealappstest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Timestamp

@Entity(tableName = "training_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
//    val date: Timestamp? = null,
): Serializable
