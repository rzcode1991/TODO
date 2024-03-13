package com.example.delightfultodoapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delightfultodoapp.model.Todo
import com.example.delightfultodoapp.ui.theme.darkGreen
import com.example.delightfultodoapp.ui.theme.lightGreen
import com.example.delightfultodoapp.viewModel.TodoListEvent

@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier,
    onEvent: (TodoListEvent) -> Unit
){

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = lightGreen)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = todo.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            todo.description?.let {
                Spacer(modifier = modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = darkGreen)
                        .padding(8.dp)
                ) {
                    Text(text = it)
                }
            }
        }
        Row {
            IconButton(onClick = {
                onEvent(TodoListEvent.OnDeleteTodoClick(todo))
            }) {
                Icon(Icons.Filled.Delete, contentDescription = "delete")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { isChecked ->
                    onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
                }
            )
        }
    }

}