package com.example.todo.viewModel

import androidx.compose.runtime.MutableState
import com.example.todo.model.Todo

// from UI to ViewModel
sealed class TodoListEvent {

    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()

}