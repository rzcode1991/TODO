package com.example.todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.ui.theme.badAssGreen
import com.example.todo.ui.theme.creamy
import com.example.todo.ui.theme.darkBrown
import com.example.todo.ui.theme.lightBrown
import com.example.todo.viewModel.TodoListEvent
import com.example.todo.viewModel.TodoListViewModel
import com.example.todo.viewModel.UiEvent
import kotlinx.coroutines.launch

@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
){

    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.navigate -> {
                    onNavigate(event)
                }
                is UiEvent.showSnackBar -> {
                    val snackBarResult = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                        )
                    /*if (snackBarResult == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }*/
                    when (snackBarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                        }
                        SnackbarResult.Dismissed -> {
                            snackbarHostState.showSnackbar(
                                message = "note deleted.",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = badAssGreen,
                contentColor = darkBrown,
                onClick = {
                viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = creamy)
                .padding(bottom = 24.dp)
        ){
            items(todos.value){todo ->
                TodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                        }
                        .padding(16.dp)
                )
            }
        }
    }



}














