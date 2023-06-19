package com.ds.todoapp.features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Todo(
    val title: String,
    val createdDate: Long,
    val targetDate: String,
    val isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int=0
):Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Todo
        return id == other.id &&
                title == other.title &&
                isCompleted == other.isCompleted
    }

    // Override the hashCode() method to include the isCompleted parameter
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + isCompleted.hashCode()
        return result
    }
}