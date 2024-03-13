package com.example.delightfultodoapp.viewModel

import com.example.delightfultodoapp.model.Todo

sealed class DoneTodosEvent {

    data class RemoveTodoFromDone(val todo: Todo): DoneTodosEvent()
    object UndoRemoveFromDone: DoneTodosEvent()

}