package com.ds.todoapp.features.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.todoapp.R
import com.ds.todoapp.features.domain.model.TaskInputModel
import com.ds.todoapp.features.domain.model.Todo
import com.ds.todoapp.features.domain.use_cases.TodoUseCases
import com.ds.todoapp.features.domain.util.DateUtil
import com.ds.todoapp.features.domain.util.DateUtil.HH_MM_AA
import com.ds.todoapp.features.domain.util.DateUtil.TIME_FORMAT_REGEX
import com.ds.todoapp.features.domain.util.TodoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {

    init {
        getTodo(TodoOrder.CreatedDate)
    }


    private val _todoFlow = MutableSharedFlow<List<Todo>>()
    val todoFlow: SharedFlow<List<Todo>> = _todoFlow

    private val _errorFlow = MutableSharedFlow<Int>()
    val errorFlow: SharedFlow<Int> = _errorFlow
    private val _responseFlow = MutableSharedFlow<Int>()
    val responseFlow: SharedFlow<Int> = _responseFlow
    fun processInputAndResponse(taskInputModel: TaskInputModel) {
        val validationResponse = isValid(taskInputModel)
        viewModelScope.launch {
            if (validationResponse != null) {
                _errorFlow.emit(validationResponse)
            } else {
                insertTask(taskInputModel)
                _responseFlow.emit(R.string.task_added_successfully)
            }
        }
    }

    private fun isValid(taskInputModel: TaskInputModel): Int? {
        if (taskInputModel.taskTitle.isEmpty()) {
            return R.string.task_title_empty
        } else if (taskInputModel.taskTime.isEmpty()) {
            return R.string.task_date_empty
        } else if (!DateUtil.isValidTimeFormat(
                "${taskInputModel.taskTime} ${taskInputModel.todoHourFormat}",
                TIME_FORMAT_REGEX
            )
        ) {
            return R.string.task_date_invalid
        } else if (DateUtil.compareTimeWithCurrentFormat(
                "${taskInputModel.taskTime} ${taskInputModel.todoHourFormat}",
                HH_MM_AA
            )
        ) {
            return R.string.deadline_extend
        }
        return null
    }

    private fun insertTask(taskInputModel: TaskInputModel) {
        viewModelScope.launch {
            todoUseCases.addTodo(
                Todo(
                    title = taskInputModel.taskTitle,
                    createdDate = System.currentTimeMillis(),
                    targetDate = "${taskInputModel.taskTime} ${taskInputModel.todoHourFormat}",
                    isCompleted = false
                )
            )
        }
    }

    fun getTodo(orderFormat: TodoOrder) {
        viewModelScope.launch {
            todoUseCases.getTodos(todoOrder = orderFormat).collect {
                _todoFlow.emit(it)
            }
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoUseCases.updateTodo(todo = todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoUseCases.deleteTodo(todo = todo)
        }
    }
}