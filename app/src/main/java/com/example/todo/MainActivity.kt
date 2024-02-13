package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todo.ui.theme.TODOTheme
import com.example.todo.ui.theme.badAssGreen
import com.example.todo.ui.theme.creamy
import com.example.todo.ui.theme.darkBrown
import com.example.todo.ui.theme.lightBrown
import com.example.todo.view.AddEditTodoScreen
import com.example.todo.view.DoneTodosScreen
import com.example.todo.view.SetUpNavHost
import com.example.todo.view.TodoListScreen
import com.example.todo.viewModel.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOTheme {

                IncludesDoneScreen()

            }
        }
    }
}


@Composable
fun IncludesDoneScreen(){

    val navController = rememberNavController()

    var homeState by remember {
        mutableStateOf(true)
    }
    var doneState by remember {
        mutableStateOf(false)
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = lightBrown,
                contentColor = badAssGreen
            ) {
                NavigationBarItem(
                    selected = homeState,
                    onClick = {
                        homeState = true
                        doneState = false
                        navController.navigate(Routes.TODO_LIST)
                    },
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = "")
                    }
                )
                NavigationBarItem(
                    selected = doneState,
                    onClick = {
                        doneState = true
                        homeState = false
                        navController.navigate(Routes.DONE_TODOS)
                    },
                    icon = {
                        Icon(Icons.Filled.Done, contentDescription = "")
                    }
                )
            }
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            SetUpNavHost(navController)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TODOTheme {
        //
    }
}