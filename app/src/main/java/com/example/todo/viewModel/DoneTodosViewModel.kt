package com.example.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoneTodosViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.getAllTodos()
    private val _uiEvents = Channel<UiEvent>()
    val uiEvents = _uiEvents.receiveAsFlow()
    private var removedTodo: Todo? = null

    fun onEvent(event: DoneTodosEvent){
        when(event){
            is DoneTodosEvent.RemoveTodoFromDone -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo.copy(isDone = false))
                    removedTodo = event.todo
                    _uiEvents.send(UiEvent.showSnackBar(
                        message = "Todo removed from done list.",
                        action = "undo")
                    )
                }
            }
            is DoneTodosEvent.UndoRemoveFromDone -> {
                viewModelScope.launch {
                    removedTodo?.let {
                        repository.insertTodo(it.copy(isDone = true))
                    }
                    _uiEvents.send(UiEvent.showSnackBar(
                        message = "Todo is still in done list.")
                    )
                }
            }
        }
    }



}