package com.ds.todoapp.features.presentation

import androidx.lifecycle.ViewModel
import com.ds.todoapp.features.domain.use_cases.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {
}