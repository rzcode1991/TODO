package com.example.delightfultodoapp.model

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: TodoDao): TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoByID(id: Int): Todo? {
        return dao.getTodoByID(id)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos()
    }
}