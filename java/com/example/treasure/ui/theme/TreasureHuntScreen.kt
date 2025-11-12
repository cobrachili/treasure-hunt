/*
Stephan Gonzales / gonzstep@oregonstate.edu
CS 492 / Oregon state University

 */
package com.example.treasure.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient


// App navigation between screens
@Composable
fun AppNavigation(
    TreasureViewModel: TreasureViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    fusedLocationClient: FusedLocationProviderClient
) {

    NavHost(navController = navController,
        startDestination = "start")
    {
        composable("start") {
            StartPage(navController,
                TreasureViewModel
            )
        }
        composable("first_clue") {
            FirstCluePage(navController,
                TreasureViewModel,
                fusedLocationClient)
        }
        composable("solved") {
            SolvedPage(navController,
                TreasureViewModel
                )
        }
        composable("second_clue") {
            SecondCluePage(navController,
                TreasureViewModel,
                fusedLocationClient
            )
        }
        composable("completed") {
            CompletedPage(navController,
                TreasureViewModel
            )
        }
    }
}
