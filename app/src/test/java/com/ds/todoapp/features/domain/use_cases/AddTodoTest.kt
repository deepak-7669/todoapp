package com.ds.todoapp.features.domain.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddTodoTest {

    @Mock
    private lateinit var repository: TodoRepository

    private lateinit var addTodo: AddTodo

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @After
    fun cleanup() {
        // Reset the main dispatcher after the test
        Dispatchers.resetMain()
    }

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        addTodo = AddTodo(repository)
    }

    @Test
    fun `AddTodo - calls insertTodo in repository`() = runTest {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "Task 1",
            createdDate = 1623897600000,
            targetDate = "2023-06-17",
            isCompleted = false
        )

        // Act
        addTodo.invoke(todo)

        // Assert
        verify(repository).insertTodo(todo)
    }
}