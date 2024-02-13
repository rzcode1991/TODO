package com.example.todo.viewModel

sealed class AddEditTodoEvent {

    data class OnTitleChanged(val title: String): AddEditTodoEvent()
    data class OnDescriptionChange(val description: String): AddEditTodoEvent()
    object OnSaveTodo: AddEditTodoEvent()

}