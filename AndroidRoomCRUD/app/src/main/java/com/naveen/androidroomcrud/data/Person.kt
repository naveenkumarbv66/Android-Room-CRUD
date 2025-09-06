package com.naveen.androidroomcrud.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val age: Int,
    val address: String,
    val salary: Double = 0.0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
