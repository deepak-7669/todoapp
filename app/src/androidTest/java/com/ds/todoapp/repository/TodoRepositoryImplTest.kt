package com.ds.todoapp.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ds.todoapp.features.data.data_source.TodoDao
import com.ds.todoapp.features.data.data_source.TodoDatabase
import com.ds.todoapp.features.data.repository.TodoRepositoryImpl
import com.ds.todoapp.features.domain.model.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TodoRepositoryImplTest {

    private lateinit var database: TodoDatabase
    private lateinit var dao: TodoDao
    private lateinit var repository: TodoRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java).build()
        dao = database.todoDao
        repository = TodoRepositoryImpl(dao)
    }

    @After
    @Throws(IOException::class)
    fun cleanup() {
        database.close()
    }

    @Test
    fun getTodoItems() = runBlocking {
        // Arrange
        val todoList =
            Todo(
                id = 1,
                title = "Task 1",
                createdDate = System.currentTimeMillis(),
                targetDate = "2023-06-17",
                isCompleted = false
            )


        dao.insertTodo(todoList)

        // Act
        val result = repository.getTodoItems().first()

        // Assert
        assertEquals(todoList, result[0])
    }

    @Test
    fun insertTodo() = runBlocking {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "Task 1",
            createdDate = System.currentTimeMillis(),
            targetDate = "2023-06-17",
            isCompleted = false
        )

        // Act
        repository.insertTodo(todo)

        // Assert
        val result = dao.getTodoItems().first()
        assertEquals(listOf(todo), result)
    }

    @Test
    fun updateTodo() = runBlocking {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "Task 1",
            createdDate = System.currentTimeMillis(),
            targetDate = "2023-06-17",
            isCompleted = false
        )
        dao.insertTodo(todo)

        // Act
        val updatedTodo = todo.copy(title = "Updated Task")
        repository.updateTodo(updatedTodo)

        // Assert
        val result = dao.getTodoItems().first()
        assertEquals(listOf(updatedTodo), result)
    }

    @Test
    fun deleteTodo() = runBlocking {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "Task 1",
            createdDate = System.currentTimeMillis(),
            targetDate = "2023-06-17",
            isCompleted = false
        )
        dao.insertTodo(todo)

        // Act
        repository.deleteTodo(todo)

        // Assert
        val result = dao.getTodoItems().first()
        assertEquals(emptyList<Todo>(), result)
    }
}
