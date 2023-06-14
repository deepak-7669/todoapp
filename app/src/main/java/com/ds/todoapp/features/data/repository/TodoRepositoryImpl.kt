package com.ds.todoapp.features.data.repository

import com.ds.todoapp.features.data.data_source.TodoDao
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override fun getTodoItems(): Flow<List<Todo>> {
        return dao.getTodoItems()
    }

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo = todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        dao.updateTodo(todo = todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo = todo)
    }
}