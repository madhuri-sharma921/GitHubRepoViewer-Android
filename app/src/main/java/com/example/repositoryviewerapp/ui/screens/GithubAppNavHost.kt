package com.example.repositoryviewerapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.repositoryviewerapp.data.repository.RepositorySearchScreen
import com.example.repositoryviewerapp.ui.screens.home.HomeScreen

@Composable
fun GithubAppNavHost(
    showRepositorySearchScreen: Boolean = false,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            if (showRepositorySearchScreen) {
                RepositorySearchScreen()
            } else {
                HomeScreen(
                    onRepositoryClick = { repositoryId ->
                        navController.navigate("details/$repositoryId")
                    }
                )
            }
        }
        composable(
            route = "details/{repositoryId}",
        ) {

        }
    }
}
