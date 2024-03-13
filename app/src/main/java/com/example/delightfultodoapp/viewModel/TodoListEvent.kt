package com.example.delightfultodoapp.viewModel

import com.example.delightfultodoapp.model.Todo

// from UI to ViewModel
sealed class TodoListEvent {

    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    object OnAddTodoClick: TodoListEvent()

}