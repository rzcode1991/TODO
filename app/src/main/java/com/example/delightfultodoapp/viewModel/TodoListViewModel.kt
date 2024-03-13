package com.example.delightfultodoapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delightfultodoapp.model.Todo
import com.example.delightfultodoapp.model.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getAllTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private var deletedTodo: Todo? = null

    // from UI to ViewModel
    fun onEvent(event: TodoListEvent){
        when(event){
            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    _uiEvent.send(UiEvent.showSnackBar("todo deleted", "undo"))
                }
            }
            is TodoListEvent.OnTodoClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
                }
            }
            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo.copy(isDone = event.isDone))
                }
            }
            is TodoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
            // tap on fab
            is TodoListEvent.OnAddTodoClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.navigate(Routes.ADD_EDIT_TODO))
                }
            }
        }
    }

}