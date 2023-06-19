package com.ds.todoapp.features.domain.use_cases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.repository.TodoRepository
import com.ds.todoapp.features.domain.util.TodoOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetTodoTest {
    @Mock
    private lateinit var repository: TodoRepository

    private lateinit var getTodos: GetTodos

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
        getTodos = GetTodos(repository)
    }

    // Arrange
    val todos = listOf(
        Todo(
            id = 1,
            title = "Task 1",
            createdDate = 1623897600000,
            targetDate = "2023-06-17",
            isCompleted = false
        ),
        Todo(
            id = 2,
            title = "Task 2",
            createdDate = 1623984000000,
            targetDate = "2023-06-18",
            isCompleted = false
        ),
        Todo(
            id = 1,
            title = "Task 1",
            createdDate = 1624070400000,
            targetDate = "2023-06-19",
            isCompleted = false
        )
    )

    @Test
    fun `GetTodos - returns sorted list by target date`() = runTest {

        `when`(repository.getTodoItems()).thenReturn(flowOf(todos))

        // Act
        val result = getTodos.invoke(TodoOrder.TargetDate)

        // Assert
        val sortedTodos = todos.sortedBy { it.targetDate }
        assertEquals(sortedTodos, result.first())
    }

    @Test
    fun `GetTodos - returns sorted list by created date`() = runTest {
        `when`(repository.getTodoItems()).thenReturn(flowOf(todos))

        val result = getTodos.invoke(TodoOrder.CreatedDate)

        val sortedTodos = todos.sortedBy { it.createdDate }
        assertEquals(sortedTodos, result.first())
    }

}