package com.example.delightfultodoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.delightfultodoapp.view.AddEditTodoScreen
import com.example.delightfultodoapp.view.DoneTodosScreen
import com.example.delightfultodoapp.view.TodoListScreen
import com.example.delightfultodoapp.viewModel.Routes

@Composable
fun SetUpNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Routes.TODO_LIST,
    ){
        composable(
            route = Routes.TODO_LIST
        ){
            TodoListScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
            arguments = listOf(
                navArgument("todoId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            AddEditTodoScreen(onBackStack = {
                navController.popBackStack()
            })
        }
        composable(
            route = Routes.DONE_TODOS
        ){
            DoneTodosScreen()
        }
    }
}
