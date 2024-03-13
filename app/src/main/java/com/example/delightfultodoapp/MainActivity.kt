package com.example.delightfultodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.delightfultodoapp.navigation.NavigationBarSection
import com.example.delightfultodoapp.navigation.SetUpNavHost
import com.example.delightfultodoapp.ui.theme.TODOTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TODOTheme {

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        NavigationBarSection(
                            navController = navController,
                            onClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {

                        SetUpNavHost(navController)

                    }
                }

            }
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