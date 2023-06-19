package com.ds.todoapp.features.domain.use_cases

import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateTodoTest {
    @Mock
    private lateinit var repository: TodoRepository

    private lateinit var updateTodo: UpdateTodo
    @Before
    fun setup() {
        updateTodo = UpdateTodo(repository)
    }

    @Test
    fun `updateTodo should call repository's updateTodo method`() = runTest {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "Task 1",
            createdDate = System.currentTimeMillis(),
            targetDate = "2023-06-17",
            isCompleted = false
        )

        // Assume the repository's updateTodo method is successful
        `when`(repository.updateTodo(todo)).thenReturn(Unit)

        // Act
        updateTodo(todo)

        // Assert
        // Verify that the repository's updateTodo method was called with the correct argument
        verify(repository).updateTodo(todo)
        // Add additional assertions if needed
    }
}