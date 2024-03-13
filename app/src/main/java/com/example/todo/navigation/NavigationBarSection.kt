package com.example.todo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo.ui.theme.badAssGreen
import com.example.todo.ui.theme.lightBrown
import com.example.todo.viewModel.Routes

@Composable
fun NavigationBarSection(
    navController: NavHostController,
    onClick: (NavigationItem) -> Unit
){

    val navItems = listOf(
        NavigationItem(
            name = "TODOs",
            route = Routes.TODO_LIST,
            icon = Icons.Default.Home
        ),
        NavigationItem(
            name = "DONE TODOs",
            route = Routes.DONE_TODOS,
            icon = Icons.Default.Done
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    val showBottomBar = backStackEntry.value?.destination?.route in navItems.map { it.route }

    if (showBottomBar){
        NavigationBar(
            containerColor = lightBrown,
            contentColor = badAssGreen
        ) {
            navItems.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        onClick(item)
                    },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = ""
                        )
                    }
                )
            }

        }
    }

}