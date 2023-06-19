package com.ds.todoapp.features.domain.use_cases

import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository
import com.ds.todoapp.features.domain.util.TodoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodos(
    private val repository: TodoRepository
) {
    operator fun invoke(
        todoOrder: TodoOrder = TodoOrder.CreatedDate
    ): Flow<List<Todo>> {
        return repository.getTodoItems().map { todos ->
            when (todoOrder) {
                is TodoOrder.TargetDate -> {
                    todos.sortedBy { it.targetDate }
                }

                is TodoOrder.CreatedDate -> {
                    todos.sortedBy { it.createdDate }
                }
            }
        }
    }
}