package com.example.todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.model.Todo
import com.example.todo.ui.theme.creamy
import com.example.todo.viewModel.DoneTodosEvent
import com.example.todo.viewModel.DoneTodosViewModel
import com.example.todo.viewModel.TodoListEvent
import com.example.todo.viewModel.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun DoneTodosScreen(
    viewModel: DoneTodosViewModel = hiltViewModel()
){

    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val doneTodos = todos.value.filter {
        it.isDone
    }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true){
        viewModel.uiEvents.collect{event ->
            when(event){
                is UiEvent.showSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(DoneTodosEvent.UndoRemoveFromDone)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = creamy)
                .padding(bottom = 24.dp)
        ){
            items(doneTodos){todo ->
                DoneTodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }


}