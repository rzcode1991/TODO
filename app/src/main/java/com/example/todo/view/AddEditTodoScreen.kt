package com.example.todo.view

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo.ui.theme.badAssGreen
import com.example.todo.ui.theme.creamy
import com.example.todo.ui.theme.darkBrown
import com.example.todo.ui.theme.darkGreen
import com.example.todo.ui.theme.lightGreen
import com.example.todo.viewModel.AddEditTodoEvent
import com.example.todo.viewModel.AddEditTodoViewModel
import com.example.todo.viewModel.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    viewModel: AddEditTodoViewModel = hiltViewModel(),
    onBackStack: () -> Unit
){

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.popBackStack -> {
                    onBackStack()
                }
                is UiEvent.showSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                        )
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
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodo)
            }) {
                Icon(Icons.Filled.Done, contentDescription = "save")
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = creamy)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
        ) {
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = darkGreen,
                    unfocusedContainerColor = lightGreen,
                    disabledContainerColor = Color.LightGray,
                ),
                singleLine = true,
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChanged(it))
                },
                placeholder = {
                    Text(text = "title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = darkGreen,
                    unfocusedContainerColor = lightGreen,
                    disabledContainerColor = Color.LightGray,
                ),
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }


}











