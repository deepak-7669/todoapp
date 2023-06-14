package com.ds.todoapp.features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val createdDate: Long,
    val targetDate: Long,
    val isCompleted: Boolean = false,
    @PrimaryKey val id: Int? = null
)