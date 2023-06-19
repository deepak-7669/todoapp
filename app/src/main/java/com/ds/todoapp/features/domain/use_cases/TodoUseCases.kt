package com.ds.todoapp.features.domain.use_cases

data class TodoUseCases(
    val getTodos: GetTodos,
    val deleteTodo: DeleteTodo,
    val addTodo: AddTodo,
    val updateTodo: UpdateTodo
)
