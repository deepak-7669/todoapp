package com.ds.todoapp.features.domain.util


sealed class TodoOrder {
    object CreatedDate: TodoOrder()
    object TargetDate : TodoOrder()
}
