package com.ds.todoapp.features.domain.use_cases

/**
 * This Use cases class define the list of use cases.
 */
data class TodoUseCases(
    val getTodos: GetTodos,
    val deleteTodo: DeleteTodo,
    val addTodo: AddTodo,
    val updateTodo: UpdateTodo
)
