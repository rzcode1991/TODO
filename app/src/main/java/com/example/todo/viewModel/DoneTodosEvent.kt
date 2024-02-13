package com.example.todo.viewModel

import com.example.todo.model.Todo

sealed class DoneTodosEvent {

    data class RemoveTodoFromDone(val todo: Todo): DoneTodosEvent()
    object UndoRemoveFromDone: DoneTodosEvent()

}