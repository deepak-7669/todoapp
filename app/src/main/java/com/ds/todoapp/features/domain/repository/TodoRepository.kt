package com.ds.todoapp.features.domain.repository

import com.ds.todoapp.features.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodoItems(): Flow<List<Todo>>

    suspend fun insertTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}