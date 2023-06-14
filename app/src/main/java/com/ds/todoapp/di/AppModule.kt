package com.ds.todoapp.di

import android.app.Application
import androidx.room.Room
import com.ds.todoapp.features.data.data_source.TodoDatabase
import com.ds.todoapp.features.data.repository.TodoRepositoryImpl
import com.ds.todoapp.features.domain.repository.TodoRepository
import com.ds.todoapp.features.domain.use_cases.AddTodo
import com.ds.todoapp.features.domain.use_cases.DeleteTodo
import com.ds.todoapp.features.domain.use_cases.GetTodos
import com.ds.todoapp.features.domain.use_cases.TodoUseCases
import com.ds.todoapp.features.domain.use_cases.UpdateTodo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodos = GetTodos(repository),
            deleteTodo = DeleteTodo(repository),
            addTodo = AddTodo(repository),
            updateTodo = UpdateTodo(repository)
        )
    }
}