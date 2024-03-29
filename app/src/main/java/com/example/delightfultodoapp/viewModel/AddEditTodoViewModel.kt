package com.example.delightfultodoapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if (todoId != -1){
            viewModelScope.launch {
                repository.getTodoByID(todoId)?.let {todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnTitleChanged -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnSaveTodo -> {
                viewModelScope.launch {
                    if (title.isBlank()){
                        _uiEvent.send(UiEvent.showSnackBar(message = "title could not be empty!"))
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    _uiEvent.send(UiEvent.popBackStack)
                }
            }
        }
    }





}