package com.ds.todoapp.features.domain.use_cases

import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository

class DeleteTodo(
    private val repository: TodoRepository
) {

    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}