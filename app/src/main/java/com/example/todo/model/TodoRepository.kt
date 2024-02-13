package com.example.todo.model

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoByID(id: Int): Todo?

    fun getAllTodos(): Flow<List<Todo>>

}